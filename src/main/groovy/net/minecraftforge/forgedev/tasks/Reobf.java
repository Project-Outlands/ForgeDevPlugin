/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */
package net.minecraftforge.forgedev.tasks;

import net.minecraftforge.forgedev.Tools;
import org.gradle.api.file.ConfigurableFileCollection;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.PathSensitive;
import org.gradle.api.tasks.PathSensitivity;

import javax.inject.Inject;

/**
 * Renames a jar from one mapping set to another, using Forge's {@code renamer} tool.
 * <p>
 * Most useful for legacy setups that need a 'reobfuscated' (named -&gt; SRG/obf) jar, for example to feed the binary
 * patch generator.
 */
public abstract class Reobf extends ToolExec implements SingleFileOutput {
    /** The jar to rename. */
    public abstract @InputFiles @PathSensitive(PathSensitivity.NONE) ConfigurableFileCollection getInput();

    /** The mappings to apply, in TSRG (or any format the renamer understands) format. */
    public abstract @InputFile @PathSensitive(PathSensitivity.NONE) RegularFileProperty getMappings();

    public abstract @Override @OutputFile RegularFileProperty getOutput();

    /// Pass {@code --disable-abstract-param}, which stops the renamer from renaming parameters of abstract methods.
    public abstract @Input @Optional Property<Boolean> getDisableAbstractParams();

    @Inject
    public Reobf() {
        super(Tools.REOBF);
        this.getOutput().convention(this.getDefaultOutputFile());
    }

    @Override
    protected void addArguments() {
        this.args(
            "--input", getInput().getSingleFile().getAbsolutePath(),
            "--output", getOutput().get().getAsFile().getAbsolutePath(),
            "--map", getMappings().get().getAsFile().getAbsolutePath()
        );

        if (this.getDisableAbstractParams().getOrElse(false))
            this.args("--disable-abstract-param");

        super.addArguments();
    }
}
