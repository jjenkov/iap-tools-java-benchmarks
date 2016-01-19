package com.jenkov.iap.other;

import com.jenkov.iap.ion.write.IonWriter;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Created by jjenkov on 15-12-2015.
 */
public class IonWriterStaticExamples {

    public static void main(String[] args) {
        writeBoolean();
    }

    private static void writeBytes() {
        byte[] src  = new byte[128];

        byte[] dest = new byte[10 * 1024];
        int destOffset = 0;

        int bytesWritten = IonWriter.writeBytes(dest, destOffset, src);
    }

    private static void writeBoolean() {
        byte[] dest = new byte[10 * 1024];
        int destOffset = 0;

        int bytesWritten = IonWriter.writeBoolean(dest, destOffset, true);
    }

    private static void writeInt64() {
        byte[] dest = new byte[10 * 1024];
        int destOffset = 0;

        int value = 123456;
        int bytesWritten = IonWriter.writeInt64(dest, destOffset, value);
    }

    private static void writeFloat32() {
        byte[] dest = new byte[10 * 1024];
        int destOffset = 0;

        float value = 12.34f;
        int bytesWritten = IonWriter.writeFloat32(dest, destOffset, value);
    }

    private static void writeFloat64() {
        byte[] dest = new byte[10 * 1024];
        int destOffset = 0;

        double value = 1234.5678d;
        int bytesWritten = IonWriter.writeFloat64(dest, destOffset, value);
    }

    private static void writeUtf8() {
        byte[] dest = new byte[10 * 1024];
        int destOffset = 0;

        String value = "A String";
        int bytesWritten = IonWriter.writeUtf8(dest, destOffset, value);
    }

    private static void writeUtf8Bytes() throws Exception {
        byte[] dest = new byte[10 * 1024];
        int destOffset = 0;

        byte[] value = "A String".getBytes("UTF-8");
        int bytesWritten = IonWriter.writeUtf8(dest, destOffset, value);
    }

    public static void writeUtc() {
        byte[] dest = new byte[10 * 1024];
        int destOffset = 0;

        Calendar value = new GregorianCalendar();
        value.setTimeZone(TimeZone.getTimeZone("UTC"));
        int dateTimeLength = 7;
        int bytesWritten = IonWriter.writeUtc(dest, destOffset, value, dateTimeLength);
    }

    public static void writeMultipleFields() {
        byte[] dest = new byte[10 * 1024];
        int destOffset = 0;

        int index = 0;
        index += IonWriter.writeInt64  (dest, destOffset, 123456);
        index += IonWriter.writeFloat32(dest, destOffset, 123.456f);
        index += IonWriter.writeFloat64(dest, destOffset, 987.654d);
    }

    public static void writeObject() {
        byte[] dest = new byte[10 * 1024];
        int destOffset = 0;

        int index = 0;

        int objectStartIndex = index;
        int lengthLength = 2;

        index += IonWriter.writeObjectBegin(dest, index, lengthLength);

        index += IonWriter.writeKey(dest, index, "field0");
        index += IonWriter.writeInt64  (dest, destOffset, 123456);

        index += IonWriter.writeKey(dest, index, "field1");
        index += IonWriter.writeFloat32(dest, destOffset, 123.456f);

        // index - objectStartIndex = full byte length of Object field,
        int objectFullByteLength = index - objectStartIndex;

        // Length of the value (body contents) of an object should not include
        // the ION field lead byte (1 byte) or the length bytes (lengthLength number of bytes)
        int objectBodyByteLength = objectFullByteLength - 1 - lengthLength;

        IonWriter.writeObjectEnd(dest, objectStartIndex, lengthLength, objectBodyByteLength);
    }


    public static void writeTable() {
        byte[] dest = new byte[10 * 1024];
        int destOffset = 0;

        int index = 0;

        int tableStartIndex = index;
        int lengthLength = 2;

        index += IonWriter.writeTableBegin(dest, index, lengthLength);

        index += IonWriter.writeKey(dest, index, "field0");
        index += IonWriter.writeKey(dest, index, "field1");

        index += IonWriter.writeInt64  (dest, destOffset, 123456);
        index += IonWriter.writeFloat32(dest, destOffset, 123.456f);

        index += IonWriter.writeInt64  (dest, destOffset, 123456);
        index += IonWriter.writeFloat32(dest, destOffset, 123.456f);

        // index - tableStartIndex = full byte length of Table field,
        int tableFullByteLength = index - tableStartIndex;

        // Length of the value (table contents) of an Table should not include
        // the ION field lead byte (1 byte) or the length bytes (lengthLength number of bytes)
        int tableBodyByteLength = tableFullByteLength - 1 - lengthLength;

        IonWriter.writeTableEnd(dest, tableStartIndex, lengthLength, tableBodyByteLength);
    }


    public static void writeArray() {
        byte[] dest = new byte[10 * 1024];
        int destOffset = 0;

        int index = 0;

        int arrayStartIndex = index;
        int lengthLength = 2;

        index += IonWriter.writeArrayBegin(dest, index, lengthLength);

        // element count obligatory, and must be before element fields.
        int arrayElementCount = 3;
        index += IonWriter.writeInt64  (dest, destOffset, arrayElementCount);

        index += IonWriter.writeInt64  (dest, destOffset, 123);
        index += IonWriter.writeInt64  (dest, destOffset, 456);
        index += IonWriter.writeInt64  (dest, destOffset, 789);

        // index - arrayStartIndex = full byte length of Table field,
        int arrayFullByteLength = index - arrayStartIndex;

        // Length of the value (array contents) of an Array should not include
        // the ION field lead byte (1 byte) or the length bytes (lengthLength number of bytes)
        int arrayBodyByteLength = arrayFullByteLength - 1 - lengthLength;

        IonWriter.writeArrayEnd(dest, arrayStartIndex, lengthLength, arrayBodyByteLength);


    }


}
