package com.ncu.springboot.core.util;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.apache.commons.lang3.StringUtils;

import com.ncu.springboot.core.constant.CharsetType;
import com.ncu.springboot.core.context.RuntimeContext;

public class EnvironmentUtils {

    private static final String RUNTIME_CONFIG_ROOT_ENV_KEY = "RUNTIME_CONFIG_ROOT";

    private static final String RUNTIME_CONFIG_ROOT_DIR_NAME = "runtime_config_root";

    private static final String CLASSPATH_PREFIX = "classpath:";

    public static boolean isOnWindows() {
        String os = System.getProperty("os.name").toLowerCase();
        return os.indexOf("win") >= 0;
    }

    /**
     * 获取系统运行期配置文件路径
     *
     * @return
     */
    public static String getRuntimeConfigPath() {
        String path = System.getenv().get(RUNTIME_CONFIG_ROOT_ENV_KEY);
        if (StringUtils.isBlank(path)) {
            if (isOnWindows()) {
                path = "C:" + File.separator + RUNTIME_CONFIG_ROOT_DIR_NAME;
            } else {
                path = '/' + RUNTIME_CONFIG_ROOT_DIR_NAME;
            }
        }
        return path;
    }

    public static String findFileInRuntimeConfigDirOrClasspath(String fileName) {
        if (StringUtils.isBlank(fileName)) {
            return null;
        }
        boolean inClasspath = false;
        if (fileName.startsWith(CLASSPATH_PREFIX)) {
            inClasspath = true;
            fileName = fileName.substring(CLASSPATH_PREFIX.length());
        }
        if (fileName.startsWith("/")) {
            fileName = fileName.substring(1);
        }
        if (!inClasspath) {
            String filePath = getRuntimeConfigPath() + File.separator + fileName;
            File file = new File(filePath);
            if (file.exists()) {
                return filePath;
            }
        }
        String filePath = getClasspathRoot() + fileName;
        File file = new File(filePath);
        if (file.exists()) {
            return filePath;
        }
        return null;
    }

    /**
     * 获取Classpath根目录地址
     *
     * @return
     */
    public static String getClasspathRoot() {
        return Thread.currentThread().getContextClassLoader().getResource("").getPath();
    }

    /**
     * 获取Web部署实际根目录
     *
     * @return
     */
    public static String getWebRootPath() {
        return RuntimeContext.getServletContext().getRealPath("/");
    }

    public static void execInThread(final String command) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                exec(command);
            }
        }).start();
    }

//    public static void main(String[] args) throws IOException {
//        try {
//            List<String> locationList = new ArrayList<>();
//            locationList.add("E:\\zookeeper-3.4.6\\bin\\zkServer.cmd");
//            locationList.add("E:\\redis\\redisbin_x64\\redis.bat");
//            locationList.add("E:\\apache-activemq-5.13.3\\bin\\win64\\activemq.bat");
//            for (int i = 0; i < locationList.size(); i++) {
//                locationList.set(i, "cmd.exe /c start " + locationList.get(i));
//            }
//            String command = CollectionUtil.join(locationList, "&&");
//            exec(command);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//    }

    public static Process exec(String command, String envp, String dir)
            throws IOException {
        String regex = "\\s+";
        String args[] = null;
        String envps[] = null;
        if (!StringUtils.isEmpty(command)) {
            args = command.split(regex);
        }
        if (!StringUtils.isEmpty(envp)) {
            envps = envp.split(regex);
        }
        File file = new File(dir);
        Runtime runtime = Runtime.getRuntime();
        if (null != runtime) {
            return runtime.exec(args, envps, file);
        }
        throw new RuntimeException("Runtime.getRuntime is null . error ");
    }

    public static void exec(String command) {
        exec(command, CharsetType.CHARSET_UTF_8);
    }

    public static void exec(String command, Charset charset) {
        BufferedReader bufferedReader = null;
        try {
            Process proc = Runtime.getRuntime().exec(command);
            InputStream stderr = proc.getErrorStream();
            InputStreamReader isr = new InputStreamReader(stderr, charset);
            bufferedReader = new BufferedReader(isr);
            String line;
            System.out.println("waiting...");
            while ((line = bufferedReader.readLine()) != null)
                System.out.println(line);
            System.out.println("");
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            IOUtil.close(bufferedReader);
        }
    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
