package com.xaaef.robin.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;

/**
 * <p>
 *
 * </p>
 *
 * @author Wang Chen Chen
 * @version 1.0.1
 * @date 2021/7/20 9:11
 */

public class ConvertUtils {


    // inputStream转outputStream
    public ByteArrayOutputStream parse(final InputStream in) throws Exception {
        final ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        int ch;
        while ((ch = in.read()) != -1) {
            swapStream.write(ch);
        }
        return swapStream;
    }


    // outputStream转inputStream
    public ByteArrayInputStream parse(final OutputStream out) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos = (ByteArrayOutputStream) out;
        return new ByteArrayInputStream(baos.toByteArray());
    }


    // inputStream转String
    public String parse_String(final InputStream in) throws Exception {
        final ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        int ch;
        while ((ch = in.read()) != -1) {
            swapStream.write(ch);
        }
        return swapStream.toString();
    }


    // OutputStream 转String
    public String parse_String(final OutputStream out) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos = (ByteArrayOutputStream) out;
        final ByteArrayInputStream swapStream = new ByteArrayInputStream(baos.toByteArray());
        return swapStream.toString();
    }


    // String转inputStream
    public ByteArrayInputStream parse_inputStream(final String in) throws Exception {
        final ByteArrayInputStream input = new ByteArrayInputStream(in.getBytes());
        return input;
    }


    // String 转outputStream
    public ByteArrayOutputStream parse_outputStream(final String in) throws Exception {
        return parse(parse_inputStream(in));
    }


    /**
     * 将object转为Integer类型
     *
     * @param object
     * @return
     */
    public static Integer obj2Integer(Object object) {
        Integer in = null;
        if (object != null) {
            if (object instanceof Integer) {
                in = (Integer) object;
            } else if (object instanceof String) {
                in = Integer.parseInt((String) object);
            } else if (object instanceof Double) {
                in = (int) ((double) object);
            } else if (object instanceof Float) {
                in = (int) ((float) object);
            } else if (object instanceof BigDecimal) {
                in = ((BigDecimal) object).intValue();
            } else if (object instanceof Long) {
                in = ((Long) object).intValue();
            }
        }
        return in;
    }

}
