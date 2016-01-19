package com.jenkov.iap.other;

import com.jenkov.iap.ion.write.IonWriter;

import java.io.UnsupportedEncodingException;

/**
 * Created by jjenkov on 17-12-2015.
 */
public class IonWriterExamples {

    public static void createIonWriter() {
        byte[] dest = new byte[10 * 1024];
        IonWriter writer = new IonWriter(dest, 0);

        writer.setDestination(dest, 0);
    }

    public static void writeBytes() {
        byte[] dest = new byte[10 * 1024];
        IonWriter writer = new IonWriter(dest, 0);

        byte[] value = new byte[]{ 1, 2, 3 };

        writer.writeBytes(value);
    }

    public static void writeBoolean() {
        byte[] dest = new byte[10 * 1024];
        IonWriter writer = new IonWriter(dest, 0);

        boolean value = true;

        writer.writeBoolean(value);
    }

    public static void writeBooleanObj() {
        byte[] dest = new byte[10 * 1024];
        IonWriter writer = new IonWriter(dest, 0);

        Boolean value = new Boolean(true);

        writer.writeBooleanObj(value);
    }

    public static void writeInt64() {
        byte[] dest = new byte[10 * 1024];
        IonWriter writer = new IonWriter(dest, 0);

        long value = 123456;

        writer.writeInt64(value);
    }

    public static void writeInt64Obj() {
        byte[] dest = new byte[10 * 1024];
        IonWriter writer = new IonWriter(dest, 0);

        Long value = new Long(123456);

        writer.writeInt64Obj(value);
    }

    public static void writeFloat32() {
        byte[] dest = new byte[10 * 1024];
        IonWriter writer = new IonWriter(dest, 0);

        float value = 123.123f;

        writer.writeFloat32(value);
    }

    public static void writeFloat32Obj() {
        byte[] dest = new byte[10 * 1024];
        IonWriter writer = new IonWriter(dest, 0);

        Float value = new Float(123.123f);

        writer.writeFloat32Obj(value);
    }

    public static void writeFloat64() {
        byte[] dest = new byte[10 * 1024];
        IonWriter writer = new IonWriter(dest, 0);

        double value = 123.123d;

        writer.writeFloat64(value);
    }

    public static void writeFloat64Obj() {
        byte[] dest = new byte[10 * 1024];
        IonWriter writer = new IonWriter(dest, 0);

        Double value = new Double(123.123d);

        writer.writeFloat64Obj(value);
    }

    public static void writeUtf8() throws UnsupportedEncodingException {
        byte[] dest = new byte[10 * 1024];
        IonWriter writer = new IonWriter(dest, 0);

        String value = "Hello";

        writer.writeUtf8(value);

        byte[] valueBytes = "Hello".getBytes("UTF-8") ;

        writer.writeUtf8(valueBytes);
    }


    public static void writeObject() {
        byte[] dest = new byte[10 * 1024];
        IonWriter writer = new IonWriter(dest, 0);

        int objectStartIndex = writer.destIndex;
        int lengthLength = 2;
        writer.writeObjectBegin(lengthLength);
        writer.writeKey("field0");
        writer.writeInt64(123);

        int objectBodyLength = writer.destIndex - (objectStartIndex + 1 + lengthLength);

        writer.writeObjectEnd(objectStartIndex, lengthLength, objectBodyLength);
    }


    public static void writeTable() {
        byte[] dest = new byte[10 * 1024];
        IonWriter writer = new IonWriter(dest, 0);

        int tableStartIndex = writer.destIndex;
        int lengthLength = 2;
        writer.writeTableBegin(lengthLength);

        writer.writeKey("field0");
        writer.writeKey("field1");

        writer.writeInt64(123);
        writer.writeInt64(456);

        writer.writeInt64(111);
        writer.writeInt64(222);

        int tableBodyLength = writer.destIndex - (tableStartIndex + 1 + lengthLength);

        writer.writeTableEnd(tableStartIndex, lengthLength, tableBodyLength);
    }


    public static void writeArray() {
        byte[] dest = new byte[10 * 1024];
        IonWriter writer = new IonWriter(dest, 0);

        int arrayStartIndex = writer.destIndex;
        int lengthLength = 2;
        writer.writeArrayBegin(lengthLength);
        writer.writeInt64(3); //element count

        writer.writeInt64(456);
        writer.writeInt64(111);
        writer.writeInt64(222);

        int arrayBodyLength = writer.destIndex - (arrayStartIndex + 1 + lengthLength);

        writer.writeArrayEnd(arrayStartIndex, lengthLength, arrayBodyLength);
    }




}
