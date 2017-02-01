package com.skcs.kiosk.pinpad;

import com.skcs.kiosk.spc.Space;
import com.skcs.kiosk.spc.SpaceFactory;
import com.sun.xml.internal.ws.util.StringUtils;
import com.util.Util;
import jssc.*;

import java.io.ByteArrayOutputStream;

import static jssc.SerialPort.MASK_RXCHAR;
import static jssc.SerialPort.PURGE_RXCLEAR;

/**
 * Created by adeirwansiah on 11/14/16.
 */
public class Pinpad {
    private static final byte SOH = 0x01;
    private static final byte ACK = 0x06;
    private static final byte NAK = 0x21;
    private Thread handler = null;
    private static boolean terminate = false;
    private boolean serviceReady = false;
    private static Pinpad instance = null;
    private SerialPort serialPort = null;

    protected String SpaceTxKey = "PinpadTx";
    protected String SpaceRxKey = "PinpadRx";
    private Space sp = SpaceFactory.getSpace();
    private byte seqNo = 0;

    public String pinpadSNo;
    public String pinpadVersion;
    protected Pinpad() {
        // Exists only to defeat instantiation.
    }

    public static Pinpad getInstance() {
        if (instance == null) {
            instance = new Pinpad();
        }
        return instance;
    }

    public boolean isTerminating()
    {
        return this.terminate;
    }

    public boolean isServiceReady()
    {
        return this.serviceReady;
    }

    public boolean openPort(String portName) {
        boolean connected = false;
        serialPort = new SerialPort(portName);
        try {
            serialPort.openPort();
            serialPort.setParams(
                    SerialPort.BAUDRATE_115200,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);
            serialPort.setEventsMask(MASK_RXCHAR);
            /*
            serialPort.addEventListener((SerialPortEvent serialPortEvent) -> {
                if(serialPortEvent.isRXCHAR()){
                    try {
                        String st = serialPort.readString(serialPortEvent
                                .getEventValue());
                        System.out.println(st);

                        //Update label in ui thread
                        Platform.runLater(() -> {
                            labelValue.setText(st);
                        });

                    } catch (SerialPortException ex) {
                        Logger.getLogger(JavaFX_jssc_Uno.class.getName())
                                .log(Level.SEVERE, null, ex);
                    }

                }
            });
            */
            connected = true;

            handler = new Thread(new Handler());
            handler.setDaemon(true);
            handler.start();

            Thread.sleep(2000L);
            //getVersion(true);
        }
        catch (SerialPortException ex) {
            System.out.println("SerialPortException: " + ex.toString());
        }
        catch (InterruptedException ex) {
            System.out.println("InterruptedException: " + ex.toString());
        }
        return connected;
    }

    public void availablePort() {
        String[] serialPortNames = SerialPortList.getPortNames();
        for(String name: serialPortNames){
            System.out.println(name);
        }
    }

    public void closePort() {
        if(serialPort != null){
            try {
                serialPort.removeEventListener();
                if (serialPort.isOpened()) {
                    serialPort.closePort();
                }
            } catch (SerialPortException ex) {
                //Logger.getLogger(JavaFX_jssc_Uno.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("SerialPortException: " + ex.toString());
            }
        }
        //serialPort.purgePort(PURGE_RXCLEAR);
    }

    public boolean isOpened() {
        boolean opened = false;
        if (serialPort != null) {
            opened = serialPort.isOpened();
        }
        return opened;
    }

    /*
    public int swipeCard(int timeout, byte seqno) {
        int ret = -1;
        try {
            serialPort.purgePort(PURGE_RXCLEAR);

            long startTime = System.nanoTime();
            int maxTO = timeout;
            boolean readyForResp = false;
            int duration = (int)((System.nanoTime() - startTime) / 1000000L);
            while ((duration < maxTO)) {
                try {
                    byte[] readBuffer = serialPort.readBytes(1, maxTO - duration);
                    if (readBuffer[0] == ACK) {

                    } else if (readBuffer[0] == NAK ) {
                        serialPort.purgePort(PURGE_RXCLEAR);
                        Thread.sleep(100);
                        //serialPort.writeByte(outFrame);
                    }
                } catch (SerialPortTimeoutException ex) {}
            }
            if (!readyForResp) {

            }

            //int maxTO = cmdObj.timeout;
            //int duration = (int)((System.nanoTime() - startTime) / 1000000L);

            //int len = 1;
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return ret;
    }
    */

    public byte[] buildFrame(byte seqNo, byte[] content)
    {
        byte[] msg = new byte[5 + content.length];
        msg[0] = SOH;
        int len = content.length + 1;
        msg[1] = ((byte)(len / 256));
        msg[2] = ((byte)(len % 256));
        msg[3] = seqNo;
        System.arraycopy(content, 0, msg, 4, content.length);
        msg[(msg.length - 1)] = msg[1];
        for (int i = 2; i < msg.length - 1; i++) {
            msg[(msg.length - 1)] = ((byte)(msg[(msg.length - 1)] ^ msg[i]));
        }
        return msg;
    }

    private class CmdObject
    {
        byte[] cmd;
        int timeout;

        private CmdObject() {}
    }

    private class Handler implements Runnable
    {
        private Handler() {}

        public void run()
        {
            try
            {
                System.out.println("Comm port handler run...");
                if (!isOpened()) {
                    return;
                }
                while (!isTerminating())
                {
                    Pinpad.CmdObject cmdObj = (Pinpad.CmdObject)sp.in(SpaceTxKey, 1000L);
                    if (cmdObj != null)
                    {
                        serialPort.purgePort(PURGE_RXCLEAR);
                        long startTime = System.nanoTime();
                        if (cmdObj.cmd != null)
                        {
                            seqNo = ((byte)(seqNo + 1));
                            byte[] outFrame = buildFrame(seqNo, cmdObj.cmd);
                            serialPort.writeBytes(outFrame);

                            int maxTO = cmdObj.timeout;
                            boolean readyForResp = false;
                            int duration = (int)((System.nanoTime() - startTime) / 1000000L);
                            while ((!isTerminating()) && (duration < maxTO))
                            {
                                try
                                {
                                    byte[] readBuffer = serialPort.readBytes(1, maxTO - duration);
                                    if (readBuffer[0] == ACK)
                                    {
                                        readyForResp = true;
                                        break;
                                    }
                                    else if (readBuffer[0] == NAK)
                                    {
                                        serialPort.purgePort(PURGE_RXCLEAR);
                                        Thread.sleep(100L);
                                        serialPort.writeBytes(outFrame);
                                    }
                                }
                                catch (SerialPortTimeoutException e) {}
                                duration = (int)((System.nanoTime() - startTime) / 1000000L);
                            }

                            if (!readyForResp) {
                                continue;
                            }
                            //if (debugMode) {
                                System.out.println("GOT ACK");
                            //}
                        }

                        int maxTO = cmdObj.timeout;
                        int duration = (int)((System.nanoTime() - startTime) / 1000000L);
                        int len = 1;

                        ByteArrayOutputStream respFrame = new ByteArrayOutputStream();
                        while ((!isTerminating()) && (duration < maxTO))
                        {
                            int numBytes = serialPort.getInputBufferBytesCount();
                            if (numBytes > 0)
                            {
                                try
                                {
                                    byte[] readBuffer = serialPort.readBytes(numBytes, maxTO - duration);
                                    respFrame.write(readBuffer);
                                }
                                catch (SerialPortTimeoutException e)
                                {
                                    break;
                                }
                                if (respFrame.size() >= 4 + len)
                                {
                                    byte[] buffer = respFrame.toByteArray();
                                    if (buffer[0] == 1)
                                    {
                                        len = (0xFF & buffer[1]) << 8 | 0xFF & buffer[2];
                                        if (buffer.length >= 4 + len)
                                        {
                                            int l = (buffer.length - 1);
                                            byte[] b = buffer;
                                            for (int i = 1; i < l; i++)
                                            {
                                                b[l] = ((byte)(b[l] ^ buffer[i]));
                                            }
                                            System.out.println("buffer[len-1]=" + b[l]);
                                            //if (buffer[(buffer.length - 1)] == 0)
                                            {
                                                serialPort.writeByte(ACK);
                                                //if (debugMode) {
                                                //    System.err.println("GOT RESP");
                                                //}
                                                byte[] resp = new byte[len - 1];
                                                System.arraycopy(buffer, 4, resp, 0, resp.length);
                                                sp.out(SpaceRxKey, resp, 3000L);
                                                break;
                                            }

                                            //serialPort.writeByte(NAK);
                                            //respFrame.reset();
                                        }
                                    }
                                }
                            }
                            Thread.sleep(100L);
                            duration = (int)((System.nanoTime() - startTime) / 1000000L);
                        }
                    }
                }
            }
            catch (Exception e)
            {
                //log.error(e.getMessage(), e);
                serviceReady = false;
            }
        }
    }

    /*
    public int getVersion(boolean retry)
    {
        int ret = -1;
        this.pinpadVersion = "";
        //this.pinpadSNo = "";

        //Get version
        byte[] cmd = { 0, 3 };
        byte[] resp = exchangeMessage(cmd, 10 * 1000);
        if (resp != null)
        {
            this.pinpadVersion = new String(resp).replaceAll("[^\\p{Print}]", "");
            //info("Get VERSION: " + this.pinpadVersion);
            System.out.println("getVersion:pinpadVersion=[" + pinpadVersion + "]");
            ret = 0;
        }
        else if (retry)
        {
            try
            {
                Thread.sleep(40000L);
            }
            catch (InterruptedException e)
            {
                //log.error(e.getMessage(), e);
                System.out.println("getVersion:error=[" + e.getMessage() + "]");
            }
            resp = exchangeMessage(cmd, 2000);
            if (resp != null)
            {
                this.pinpadVersion = new String(resp).replaceAll("[^\\p{Print}]", "");
                //log.info("Get VERSION: " + this.pinpadVersion);
                System.out.println("getVersion:pinpadVersion=[" + pinpadVersion + "]");
                ret = 0;
            }
            else
            {
                //log.error("Get VERSION: TIMEOUT!");
                System.out.println("getVersion:get version timeout.");
                ret = -2;
            }
        }
        else
        {
            System.out.println("getVersion:get version timeout.");
            ret = -2;
        }
        */

        //Get serial no
        /*
        cmd = new byte[] { 0, 4 };
        resp = exchangeMessage(cmd, 2000);
        if (resp != null)
        {
            if (resp[0] == 0)
            {
                byte[] ret = new byte[resp.length - 2];
                if (ret[(ret.length - 1)] == 0) {
                    System.arraycopy(resp, 1, ret, 0, ret.length);
                } else {
                    System.arraycopy(resp, 1, ret, 0, ret.length);
                }
                this.pinpadSNo = new String(ret).replaceAll("[^\\p{Print}]", "");
                //log.info("Get Serial No: " + this.pinpadSNo);
                System.out.println("getVersion:pinpadSerialNo=[" + pinpadSNo + "]");
            }
        }
        else
        {
            //log.error("Get Serial No: TIMEOUT");
            System.out.println("getVersion:get serial no timeout.");
            return;
        }
        * /

        return ret;
    }
    */

    public PinpadResult trmGetVersion(int timeout)
    {
        PinpadResult ret = new PinpadResult(-1);
        byte[] cmd = { 0, 0x03 };
        byte[] resp = exchangeMessage(cmd, timeout * 1000);
        if (resp != null)
        {
            if (resp.length > 1) {
                byte[] rsp = new byte[resp.length - 1];
                System.arraycopy(resp, 1, rsp, 0, rsp.length);
                ret.setRespData(rsp);
                if (resp[0] == 0) {
                    ret.setResult(0);
                    System.out.println("trmGetVersion:return:" + ret.toString());
                } else {
                    ret.setResult(resp[0] * -1);
                    ret.setRespData(rsp);
                    System.out.println("trmGetVersion:error:" + ret.toString());
                }
            }
        }
        else
        {
            ret.setResult(-99);
            System.out.println("trmGetVersion:timeout");
            //log.error("getTrack2Data:timeout");
        }
        return ret;
    }

    public PinpadResult trmGetSerialNo(int timeout)
    {
        PinpadResult ret = new PinpadResult(-1);
        byte[] cmd = { 0, 0x04 };
        byte[] resp = exchangeMessage(cmd, timeout * 1000);
        if (resp != null)
        {
            if (resp[0] == 0 && resp.length > 1) {
                byte[] rsp = new byte[resp.length - 1];
                System.arraycopy(resp, 1, rsp, 0, rsp.length);
                ret.setResult(0);
                ret.setRespData(rsp);
                pinpadSNo = ret.getString();
                System.out.println("trmGetSerialNo:serial=[" + pinpadSNo + "]");
            }
        }
        else
        {
            //log.error("getSerialNo:timeout");
            System.out.println("trmGetSerialNo:timeout");
            ret.setResult(-99);
        }
        return ret;
    }

    public PinpadResult magSwipe(int timeout)
    {
        PinpadResult ret = new PinpadResult(-1);
        byte[] cmd = { 0, 0x31 };
        byte[] resp = exchangeMessage(cmd, timeout * 1000);
        if (resp != null)
        {
            if (resp.length > 1) {
                byte[] rsp = new byte[resp.length - 1];
                System.arraycopy(resp, 1, rsp, 0, rsp.length);
                ret.setRespData(rsp);
                if (resp[0] == 0) {
                    ret.setResult(0);
                    System.out.println("magSwipe:return:" + ret.toString());
                } else {
                    ret.setResult(resp[0] * -1);
                    ret.setRespData(rsp);
                    System.out.println("magSwipe:error:" + ret.toString());
                }
            }
        }
        else
        {
            ret.setResult(-99);
            System.out.println("magSwipe:timeout");
            //log.error("getTrack2Data:timeout");
        }
        return ret;
    }

    public PinpadResult pinInput(int timeout, byte minLen, byte maxLen, String prompt) throws Exception
    {
        ByteArrayOutputStream cmd = new ByteArrayOutputStream();
        cmd.write(new byte[] {0, 0x12, minLen, maxLen});
        cmd.write(prompt.getBytes());
        PinpadResult ret = new PinpadResult(-1);
        //byte[] cmd = { 0, 3 };
        byte[] resp = exchangeMessage(cmd.toByteArray(), timeout * 1000);
        if (resp != null)
        {
            if (resp.length > 1) {
                byte[] rsp = new byte[resp.length - 1];
                System.arraycopy(resp, 1, rsp, 0, rsp.length);
                ret.setRespData(rsp);
                if (resp[0] == 0) {
                    ret.setResult(0);
                    System.out.println("pinInput:return:" + Util.hexString(rsp));
                } else {
                    ret.setResult(resp[0] * -1);
                    ret.setRespData(rsp);
                    System.out.println("pinInput:error:" + ret.toString());
                }
            }
        }
        else
        {
            ret.setResult(-99);
            System.out.println("pinInput:timeout");
            //log.error("getTrack2Data:timeout");
        }
        return ret;
    }

    public PinpadResult keyInject(int timeout, byte location, byte[] data) throws Exception
    {
        ByteArrayOutputStream cmd = new ByteArrayOutputStream();
        cmd.write(new byte[] {0, 0x22});
        cmd.write(location);
        cmd.write(data);
        PinpadResult ret = new PinpadResult(-1);
        byte[] resp = exchangeMessage(cmd.toByteArray(), timeout * 1000);
        if (resp != null)
        {
            if (resp.length > 1) {
                byte[] rsp = new byte[resp.length - 1];
                System.arraycopy(resp, 1, rsp, 0, rsp.length);
                ret.setRespData(rsp);
                if (resp[0] == 0) {
                    ret.setResult(0);
                    System.out.println("keyInject:return:" + ret.toString());
                } else {
                    ret.setResult(resp[0] * -1);
                    ret.setRespData(rsp);
                    System.out.println("keyInject:error:" + ret.toString());
                }
            }
        }
        else
        {
            ret.setResult(-99);
            System.out.println("keyInject:timeout");
            //log.error("getTrack2Data:timeout");
        }
        return ret;
    }

    public PinpadResult keyEncrypt(int timeout, byte location, byte[] data) throws Exception
    {
        ByteArrayOutputStream cmd = new ByteArrayOutputStream();
        cmd.write(new byte[] {0, 0x23});
        cmd.write(location);
        cmd.write(data);
        PinpadResult ret = new PinpadResult(-1);
        byte[] resp = exchangeMessage(cmd.toByteArray(), timeout * 1000);
        if (resp != null)
        {
            if (resp.length > 1) {
                byte[] rsp = new byte[resp.length - 1];
                System.arraycopy(resp, 1, rsp, 0, rsp.length);
                ret.setRespData(rsp);
                if (resp[0] == 0) {
                    ret.setResult(0);
                    System.out.println("keyEncrypt:return:" + Util.hexString(rsp));
                } else {
                    ret.setResult(resp[0] * -1);
                    ret.setRespData(rsp);
                    System.out.println("keyEncrypt:error:" + ret.toString());
                }
            }
        }
        else
        {
            ret.setResult(-99);
            System.out.println("keyEncrypt:timeout");
            //log.error("getTrack2Data:timeout");
        }
        return ret;
    }

    public PinpadResult keyDecrypt(int timeout, byte location, byte[] data) throws Exception
    {
        ByteArrayOutputStream cmd = new ByteArrayOutputStream();
        cmd.write(new byte[] {0, 0x24});
        cmd.write(location);
        cmd.write(data);
        PinpadResult ret = new PinpadResult(-1);
        byte[] resp = exchangeMessage(cmd.toByteArray(), timeout * 1000);
        if (resp != null)
        {
            if (resp.length > 1) {
                byte[] rsp = new byte[resp.length - 1];
                System.arraycopy(resp, 1, rsp, 0, rsp.length);
                ret.setRespData(rsp);
                if (resp[0] == 0) {
                    ret.setResult(0);
                    System.out.println("keyDecrypt:return:" + Util.hexString(rsp));
                } else {
                    ret.setResult(resp[0] * -1);
                    ret.setRespData(rsp);
                    System.out.println("keyDecrypt:error:" + ret.toString());
                }
            }
        }
        else
        {
            ret.setResult(-99);
            System.out.println("keyDecrypt:timeout");
            //log.error("getTrack2Data:timeout");
        }
        return ret;
    }

    public byte[] exchangeMessage(byte[] cmd, int timeout)
    {
        if (timeout == 0) {
            timeout = 1;
        }

        CmdObject cmdObj = new CmdObject();
        cmdObj.cmd = cmd;
        cmdObj.timeout = timeout;

        sp.out(SpaceTxKey, cmdObj, 3000L);
        byte[] resp = (byte[])sp.in(SpaceRxKey, cmdObj.timeout + 100);
        if (resp == null) {
            return null;
        }

        if (resp.length == 1) {
            return new byte[] { resp[0] };
        }

        if ((cmd != null) && ((resp[0] != cmd[0]) || (resp[1] != cmd[1]))) {
            return null;
        }

        if (resp.length > 2)
        {
            byte[] ret = new byte[resp.length - 2];
            System.arraycopy(resp, 2, ret, 0, ret.length);
            return ret;
        }
        return new byte[1];
    }
}
