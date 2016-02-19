package net.tribe7.gradle.swig.task;

import org.gradle.api.InvalidUserDataException;
import org.gradle.api.tasks.*;
import java.io.File;
import java.util.*;
import static java.lang.String.format;

public class SwigTask extends Exec {

    private boolean verbose;
    private boolean enableCpp;

    private String module;
    private String packageName;

    @InputFile private File source;
    @OutputFile private File wrapperTargetFile;
    private File javaSourcesPath;
    private Collection<File> includePaths = new ArrayList<>();
    private Collection<String> symbols = new ArrayList<>();

    private final List<Object> commandArgs = new ArrayList<>();

    @Override
    protected void exec() {

        setExecutable("swig");

        notNull(module, "Missing module name.");
        notNull(packageName, "Missing java package name.");
        notNull(source, "Missing source interface/header file.");
        notNull(javaSourcesPath, "Missing java sources path.");
        notNull(wrapperTargetFile, "Missing wrapper target file.");

        isTrue(source.exists(), "Source interface/header file does not exist.");
        isTrue(source.isFile(), "Source interface/header is not a file.");

        isTrue(javaSourcesPath.exists() || javaSourcesPath.mkdirs(), "Failed to create Java sources path.");
        isTrue(javaSourcesPath.isDirectory(), "Java sources path is not a directory.");

        File wp = wrapperTargetFile.getParentFile();
        isTrue(wp.exists() || wp.mkdirs(), "Failed to create wrapper target path directory.");
        isTrue(wp.isDirectory(), "Wrapper target path is not a directory.");

        if (verbose) { arg("-v"); }
        if (enableCpp) { arg("-c++"); }

        arg("-java");
        arg("-package", packageName);
        arg("-module", module);

        for (String symbol : symbols) {
            notNull(symbol, "At least one symbol definition is missing.");
            arg(format("-D%s", symbol));
        }

        for (File includePath : includePaths) {
            notNull(includePath, "At least one include path is missing.");
            isTrue(includePath.exists(), "Include path does not exist: [%s]", includePath.getAbsolutePath());
            arg(format("-I%s", includePath.getAbsolutePath()));
        }

        arg("-o", wrapperTargetFile.getAbsolutePath());
        arg("-outdir", javaSourcesPath.getAbsolutePath());
        arg(source.getAbsolutePath());

        setArgs(commandArgs);
        super.exec();
    }

    private void arg(String key) {
        commandArgs.add(key);
    }

    private void arg(String key, String val) {
        commandArgs.add(key);
        commandArgs.add(val);
    }

    public boolean isVerbose() { return verbose; }
    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    public boolean isEnableCpp() { return enableCpp; }
    public void setEnableCpp(boolean enableCpp) {
        this.enableCpp = enableCpp;
    }

    public String getModule() { return module; }
    public void setModule(String module) {
        this.module = module;
    }

    public String getPackageName() { return packageName; }
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public File getSource() { return source; }
    public void setSource(File source) {
        this.source = source;
    }

    public File getJavaSourcesPath() { return javaSourcesPath; }
    public void setJavaSourcesPath(File javaSourcesPath) {
        this.javaSourcesPath = javaSourcesPath;
    }

    public File getWrapperTargetFile() { return wrapperTargetFile; }
    public void setWrapperTargetFile(File wrapperTargetFile) {
        this.wrapperTargetFile = wrapperTargetFile;
    }

    public Collection<File> getIncludePaths() { return includePaths; }
    public void setIncludePaths(Collection<File> includePaths) {
        this.includePaths = includePaths;
    }

    public Collection<String> getSymbols() { return symbols; }
    public void setSymbols(Collection<String> symbols) {
        this.symbols = symbols;
    }

    private void notNull(Object ref, String format, Object ... args) {
        if (ref == null) {
            throw new InvalidUserDataException(format(format, args));
        }
    }

    private void isTrue(boolean cond, String format, Object ... args) {
        if (!cond) {
            throw new InvalidUserDataException(format(format, args));
        }
    }
}
