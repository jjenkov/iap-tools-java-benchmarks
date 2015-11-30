package com.jenkov.iap.serializedlength;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.cbor.CBORFactory;
import com.jenkov.iap.pojos.*;
import com.jenkov.iap.write.IonObjectWriter;
import org.msgpack.jackson.dataformat.MessagePackFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * This class is used to measure the serialized length of ION, JSON and other data formats.
 * The same object or objects are serialized into ION, JSON etc. and the length of the serialized data measured.
 *
 *
 * Created by jjenkov on 14-11-2015.
 */
public class Main {

    public static void main(String[] args) throws IOException {
        stringLengths();
        //doubleLengths();
        //floatLengths();
        //longLengths();
        //booleanLengths();
    }

    private static void stringLengths() throws IOException {
        json(new Pojo1String());
        msgPack(new Pojo1String());
        cbor(new Pojo1String());
        ion(new Pojo1String(), 1);

        json(new PojoArray1String(10));
        msgPack(new PojoArray1String(10));
        cbor(new PojoArray1String(10));
        ion(new PojoArray1String(10), 1);

        json(new PojoArray1String(100));
        msgPack(new PojoArray1String(100));
        cbor(new PojoArray1String(100));
        ion(new PojoArray1String(100), 2);

        json(new PojoArray1String(1000));
        msgPack(new PojoArray1String(1000));
        cbor(new PojoArray1String(1000));
        ion(new PojoArray1String(1000), 2);

        json(new Pojo10String());
        msgPack(new Pojo10String());
        cbor(new Pojo10String());
        ion(new Pojo10String(), 1);

        json(new PojoArray10String(10));
        msgPack(new PojoArray10String(10));
        cbor(new PojoArray10String(10));
        ion(new PojoArray10String(10), 2);

        json(new PojoArray10String(100));
        msgPack(new PojoArray10String(100));
        cbor(new PojoArray10String(100));
        ion(new PojoArray10String(100), 2);

        json(new PojoArray10String(1000));
        msgPack(new PojoArray10String(1000));
        cbor(new PojoArray10String(1000));
        ion(new PojoArray10String(1000), 2);
    }

    private static void doubleLengths() throws IOException {
        json(new Pojo1Double());
        msgPack(new Pojo1Double());
        cbor(new Pojo1Double());
        ion(new Pojo1Double(), 1);

        json(new PojoArray1Double(10));
        msgPack(new PojoArray1Double(10));
        cbor(new PojoArray1Double(10));
        ion(new PojoArray1Double(10), 1);

        json(new PojoArray1Double(100));
        msgPack(new PojoArray1Double(100));
        cbor(new PojoArray1Double(100));
        ion(new PojoArray1Double(100), 2);

        json(new PojoArray1Double(1000));
        msgPack(new PojoArray1Double(1000));
        cbor(new PojoArray1Double(1000));
        ion(new PojoArray1Double(1000), 2);

        json(new Pojo10Double());
        msgPack(new Pojo10Double());
        cbor(new Pojo10Double());
        ion(new Pojo10Double(), 1);

        json(new PojoArray10Double(10));
        msgPack(new PojoArray10Double(10));
        cbor(new PojoArray10Double(10));
        ion(new PojoArray10Double(10), 2);

        json(new PojoArray10Double(100));
        msgPack(new PojoArray10Double(100));
        cbor(new PojoArray10Double(100));
        ion(new PojoArray10Double(100), 2);

        json(new PojoArray10Double(1000));
        msgPack(new PojoArray10Double(1000));
        cbor(new PojoArray10Double(1000));
        ion(new PojoArray10Double(1000), 2);
    }

    private static void floatLengths() throws IOException {
        json(new Pojo1Float());
        msgPack(new Pojo1Float());
        cbor(new Pojo1Float());
        ion(new Pojo1Float(), 1);

        json(new PojoArray1Float(10));
        msgPack(new PojoArray1Float(10));
        cbor(new PojoArray1Float(10));
        ion(new PojoArray1Float(10), 1);

        json(new PojoArray1Float(100));
        msgPack(new PojoArray1Float(100));
        cbor(new PojoArray1Float(100));
        ion(new PojoArray1Float(100), 2);

        json(new PojoArray1Float(1000));
        msgPack(new PojoArray1Float(1000));
        cbor(new PojoArray1Float(1000));
        ion(new PojoArray1Float(1000), 2);

        json(new Pojo10Float());
        msgPack(new Pojo10Float());
        cbor(new Pojo10Float());
        ion(new Pojo10Float(), 1);

        json(new PojoArray10Float(10));
        msgPack(new PojoArray10Float(10));
        cbor(new PojoArray10Float(10));
        ion(new PojoArray10Float(10), 2);

        json(new PojoArray10Float(100));
        msgPack(new PojoArray10Float(100));
        cbor(new PojoArray10Float(100));
        ion(new PojoArray10Float(100), 2);

        json(new PojoArray10Float(1000));
        msgPack(new PojoArray10Float(1000));
        cbor(new PojoArray10Float(1000));
        ion(new PojoArray10Float(1000), 2);
    }

    private static void longLengths() throws IOException {
        json(new Pojo1Long());
        msgPack(new Pojo1Long());
        cbor(new Pojo1Long());
        ion(new Pojo1Long(), 1);

        json(new PojoArray1Long(10));
        msgPack(new PojoArray1Long(10));
        cbor(new PojoArray1Long(10));
        ion(new PojoArray1Long(10), 1);

        json(new PojoArray1Long(100));
        msgPack(new PojoArray1Long(100));
        cbor(new PojoArray1Long(100));
        ion(new PojoArray1Long(100), 1);

        json(new PojoArray1Long(1000));
        msgPack(new PojoArray1Long(1000));
        cbor(new PojoArray1Long(1000));
        ion(new PojoArray1Long(1000), 2);

        json(new Pojo10Long());
        msgPack(new Pojo10Long());
        cbor(new Pojo10Long());
        ion(new Pojo10Long(), 1);

        json(new PojoArray10Long(10));
        msgPack(new PojoArray10Long(10));
        cbor(new PojoArray10Long(10));
        ion(new PojoArray10Long(10), 2);

        json(new PojoArray10Long(100));
        msgPack(new PojoArray10Long(100));
        cbor(new PojoArray10Long(100));
        ion(new PojoArray10Long(100), 2);

        json(new PojoArray10Long(1000));
        msgPack(new PojoArray10Long(1000));
        cbor(new PojoArray10Long(1000));
        ion(new PojoArray10Long(1000), 2);
    }

    private static void booleanLengths() throws IOException {
        json(new Pojo1Boolean());
        msgPack(new Pojo1Boolean());
        cbor(new Pojo1Boolean());
        ion(new Pojo1Boolean(), 1);

        json(new PojoArray1Boolean(10));
        msgPack(new PojoArray1Boolean(10));
        cbor(new PojoArray1Boolean(10));
        ion(new PojoArray1Boolean(10), 1);

        json(new PojoArray1Boolean(100));
        msgPack(new PojoArray1Boolean(100));
        cbor(new PojoArray1Boolean(100));
        ion(new PojoArray1Boolean(100), 1);

        json(new PojoArray1Boolean(1000));
        msgPack(new PojoArray1Boolean(1000));
        cbor(new PojoArray1Boolean(1000));
        ion(new PojoArray1Boolean(1000), 2);

        json(new Pojo10Boolean());
        msgPack(new Pojo10Boolean());
        cbor(new Pojo10Boolean());
        ion(new Pojo10Boolean(), 1);

        json(new PojoArray10Boolean(10));
        msgPack(new PojoArray10Boolean(10));
        cbor(new PojoArray10Boolean(10));
        ion(new PojoArray10Boolean(10), 1);

        json(new PojoArray10Boolean(100));
        msgPack(new PojoArray10Boolean(100));
        cbor(new PojoArray10Boolean(100));
        ion(new PojoArray10Boolean(100), 2);

        json(new PojoArray10Boolean(1000));
        msgPack(new PojoArray10Boolean(1000));
        cbor(new PojoArray10Boolean(1000));
        ion(new PojoArray10Boolean(1000), 2);
    }

    private static void json(Object src) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(out, src);

        byte[] bytes = out.toByteArray();

        System.out.println("JSON    : " + src.toString() + " : " + bytes.length);

    }

    private static void ion(Object src, int lengthLength) {
        IonObjectWriter writer = new IonObjectWriter(src.getClass());

        byte[] dest = new byte[1024 * 1024];

        int length = writer.writeObject(src, lengthLength, dest, 0);

        System.out.println("ION     : " + src.toString() + " : " + length);
    }


    private static void msgPack(Object src) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        ObjectMapper objectMapper = new ObjectMapper(new MessagePackFactory());
        objectMapper.writeValue(out, src);

        byte[] bytes = out.toByteArray();

        System.out.println("MsgPack : " + src.toString() + " : " + bytes.length);
    }

    private static void cbor(Object src) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        ObjectMapper objectMapper = new ObjectMapper(new CBORFactory());
        objectMapper.writeValue(out, src);

        byte[] bytes = out.toByteArray();

        System.out.println("CBOR    : " + src.toString() + " : " + bytes.length);
    }



}
