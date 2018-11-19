package gui;

import functions.Function;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class FunctionLoader extends ClassLoader {
    public FunctionLoader() {}

    public Object loadFunction(String path, String className) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        File file = new File(path);

        BufferedInputStream stream = new BufferedInputStream(new FileInputStream(file));
        byte[] bytes = new byte[(int) file.length()];
        stream.read(bytes);
        Class clazz = defineClass(className, bytes, 0, bytes.length);
        return clazz.getConstructor().newInstance();
    }
}
