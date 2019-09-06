package com.mgatelabs.piper.shared.util;

import com.mgatelabs.piper.shared.details.ActionType;
import com.mgatelabs.piper.shared.details.ComponentDefinition;
import com.mgatelabs.piper.shared.details.DeviceDefinition;
import com.mgatelabs.piper.shared.image.ImageWrapper;
import com.mgatelabs.piper.shared.image.RawImageWrapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.vidstige.jadb.JadbDevice;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.SecureRandom;

/**
 * Created by @mgatelabs (Michael Fuller) on 9/4/2017 for Phone-Piper
 */
public class AdbUtils {

    private static final SecureRandom RANDOM = new SecureRandom();
    private static final Logger LOGGER = LoggerFactory.getLogger(AdbUtils.class);

    public static void component(DeviceDefinition deviceDefinition, ComponentDefinition componentDefinition, ActionType type, final AdbWrapper shell, boolean batch) {
        if (!componentDefinition.isEnabled()) return;

        final String cmd;
        switch (type) {
            case TAP: {
                final int x = getStartX(componentDefinition, type);
                final int y = getStartY(componentDefinition, type);
                cmd = ("input tap " + x + " " + y);
            }
            break;
            case SWIPE_DOWN:
            case SWIPE_UP: {
                final int x1 = getStartX(componentDefinition, type);
                final int x2 = x1;

                final int y1 = getStartY(componentDefinition, type);
                final int y2 = getEndY(deviceDefinition, componentDefinition, type);
                final int time = 200;

                cmd = ("input swipe " + x1 + " " + y1 + " " + x2 + " " + y2 + " " + time);
            } break;
            case SWIPE_RIGHT:
            case SWIPE_LEFT: {
                final int x1 = getStartX(componentDefinition, type);
                final int x2 = getEndX(deviceDefinition, componentDefinition, type);

                final int y1 = getStartY(componentDefinition, type);
                final int y2 = y1;
                final int time = 100;

                cmd = ("input swipe " + x1 + " " + y1 + " " + x2 + " " + y2 + " " + time);
            }
            break;
            case SLOW_DOWN:
            case SLOW_UP: {
                final int x1 = getStartX(componentDefinition, type);
                final int x2 = x1;

                final int y1 = getStartY(componentDefinition, type);
                final int y2 = getEndY(deviceDefinition, componentDefinition, type);
                final int time = 2000;

                cmd = ("input swipe " + x1 + " " + y1 + " " + x2 + " " + y2 + " " + time);
            } break;
            case SLOW_LEFT:
            case SLOW_RIGHT: {
                final int x1 = getStartX(componentDefinition, type);
                final int x2 = getEndX(deviceDefinition, componentDefinition, type);

                final int y1 = getStartY(componentDefinition, type);
                final int y2 = y1;
                final int time = 2000;

                cmd = ("input swipe " + x1 + " " + y1 + " " + x2 + " " + y2 + " " + time);
            }
            break;
            default: {
                System.out.println("Unhandled component command: " + type.name());
            }
            return;
        }
        if (batch) {
            shell.batch(cmd);
        } else {
            shell.exec(cmd);
        }
    }

    public static boolean event(final String eventId, boolean raw, final AdbWrapper shell, final boolean batch) {
        final String cmd;
        final int event;
        if (raw) {
            event = Integer.parseInt(eventId);
        } else {
            switch (eventId.toLowerCase()) {
                case "power": {
                    event = 26;
                }
                break;
                case "wakeup": {
                    event = 224;
                }
                break;
                case "sleep": {
                    event = 223;
                }
                break;
                case "camera": {
                    event = 27;
                }
                break;
                case "call": {
                    event = 5;
                }
                break;
                case "back": {
                    event = 4;
                }
                break;
                case "brighter": {
                    event = 221;
                }
                break;
                case "darker": {
                    event = 220;
                }
                break;
                default:
                    return false;
            }
        }
        cmd = "input keyevent " + event;
        if (batch) {
            shell.batch(cmd);
        } else {
            shell.exec(cmd);
        }
        return true;
    }

    public static boolean input(final String inputId, final AdbWrapper shell, final boolean batch) {
        final String cmd;
        final int event = Integer.parseInt(inputId);
        cmd = "input keyevent " + event;
        if (batch) {
            shell.batch(cmd);
        } else {
            shell.exec(cmd);
        }
        return true;
    }

    private static int getStartX(ComponentDefinition componentDefinition, ActionType type) {
        int x = componentDefinition.getX();
        switch (type) {
            case SWIPE_LEFT: {
                x += (componentDefinition.getW() - (componentDefinition.getW() / 8));
            }
            break;
            case SLOW_LEFT: {
                x += (componentDefinition.getW());
            }
            break;
            case SWIPE_RIGHT: {
                x += (componentDefinition.getW() / 8);
            }
            break;
            case SLOW_RIGHT: {
                x += 0;
            }
            break;
            default: {
                x += RANDOM.nextInt(componentDefinition.getW());
            }
            break;
        }
        return x;
    }

    private static int getEndX(DeviceDefinition deviceDefinition, ComponentDefinition componentDefinition, ActionType type) {
        int x = componentDefinition.getX();
        switch (type) {
            case SWIPE_RIGHT: {
                return least(deviceDefinition.getWidth(), x + (componentDefinition.getW() * 2));
            }
            case SWIPE_LEFT: {
                return greater(0, x - (componentDefinition.getW()));
            }
            case SLOW_RIGHT: {
                return least(deviceDefinition.getWidth(), x + componentDefinition.getW());
            }
            case SLOW_LEFT: {
                return greater(0, x);
            }
            default: {
                return getStartX(componentDefinition, type);
            }
        }
    }

    private static int getStartY(ComponentDefinition componentDefinition, ActionType type) {
        int y = componentDefinition.getY();
        switch (type) {
            case SLOW_UP: {
                y += (componentDefinition.getH());
            }
            break;
            case SWIPE_UP: {
                y += (componentDefinition.getH() - (componentDefinition.getH() / 8));
            }
            break;
            case SLOW_DOWN: {
                y += 0;
            }
            break;
            case SWIPE_DOWN: {
                y += (componentDefinition.getH() / 8);
            }
            break;
            default: {
                y += RANDOM.nextInt(componentDefinition.getH());
            }
            break;
        }
        return y;
    }

    private static int getEndY(DeviceDefinition deviceDefinition, ComponentDefinition componentDefinition, ActionType type) {
        int y = componentDefinition.getY();
        switch (type) {
            case SLOW_DOWN: {
                return least(deviceDefinition.getHeight(), y + (componentDefinition.getH()));
            }
            case SWIPE_DOWN: {
                return least(deviceDefinition.getHeight(), y + (componentDefinition.getH() * 2));
            }
            case SLOW_UP: {
                return greater(0, y);
            }
            case SWIPE_UP: {
                return greater(0, y - (componentDefinition.getH() * 2));
            }
            default: {
                return getStartY(componentDefinition, type);
            }
        }
    }

    private static int least(int max, int value) {
        if (value > max) {
            return max;
        }
        return value;
    }

    private static int greater(int min, int value) {
        if (value < min) {
            return min;
        }
        return value;
    }

    public static boolean persistScreen(AdbWrapper device) {
        return device.exec("screencap /mnt/sdcard/framebuffer.raw");
    }

    public static ImageWrapper getScreen(AdbWrapper wr) {
        final String command;
        if (StringUtils.isNotBlank(AdbShell.ADB_DIRECT)) {
            command = String.format("adb -s %s pull exec-out screencap", AdbShell.ADB_DIRECT);
        } else {
            command = "adb exec-out screencap";
        }

        byte[] bytes = execStream(command);
        try {
            int w, h;
            if (bytes.length > 12) { // Sanity
                ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
                byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
                w = byteBuffer.getInt();
                h = byteBuffer.getInt();
            } else {
                w = 0;
                h = 0;
            }
            return new RawImageWrapper(w, h, RawImageWrapper.ImageFormats.RGBA, 12, bytes);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    public static ImageWrapper getScreenFrom(byte[] bytes) {
        try {
            int w, h;
            if (bytes.length > 12) { // Sanity
                ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
                byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
                w = byteBuffer.getInt();
                h = byteBuffer.getInt();
            } else {
                w = 0;
                h = 0;
            }
            return new RawImageWrapper(w, h, RawImageWrapper.ImageFormats.RGBA, 12, bytes);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    private static boolean execWait(final String command) {
        try {
            Process myProcess = Runtime.getRuntime().exec(command);
            myProcess.waitFor();
            return true;
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            return false;
        }
    }

    private static byte[] execStream(final String command) {
        try {
            LOGGER.trace(command);
            Process myProcess = Runtime.getRuntime().exec(command);
            return repair(myProcess.getInputStream());
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private static byte[] repair(InputStream bytesIn) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int value;
        int temp = 0;
        boolean useTemp = false;
        BufferedInputStream bufferedInputStream = new BufferedInputStream(bytesIn);
        while ((value = bufferedInputStream.read()) != -1) {
            if (useTemp && value == 0x0A) {
                outputStream.write(value);
                useTemp = false;
                continue;
            } else if (useTemp) {
                outputStream.write(temp);
                useTemp = false;
            }
            if (value == 0x0d) {
                useTemp = true;
                temp = value;
            } else {
                outputStream.write(value);
            }
        }
        outputStream.close();
        return outputStream.toByteArray();
    }
}
