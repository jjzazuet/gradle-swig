package net.tribe7.gradle.swig;

import net.tribe7.gradle.swig.task.SwigTask;
import org.gradle.api.*;

public class SwigPlugin implements Plugin<Project> {
    @Override
    public void apply(Project target) {
        target.getExtensions().getExtraProperties().set(
                SwigTask.class.getSimpleName(), SwigTask.class
        );
    }
}
