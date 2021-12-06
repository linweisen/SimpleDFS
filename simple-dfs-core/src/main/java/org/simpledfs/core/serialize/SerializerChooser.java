package org.simpledfs.core.serialize;

public class SerializerChooser {

    private SerializerChooser() {

    }

    public Serializer choose(byte serializeAlgorithm){
        return KryoSerializer.getInstance();
    }

    private static class SerializerChooserHolder {

        private static SerializerChooser chooser = new SerializerChooser();

    }
    public static SerializerChooser getInstance() {

        return SerializerChooserHolder.chooser;
    }
}
