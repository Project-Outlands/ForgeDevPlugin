/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */
package net.minecraftforge.forgedev.base;

import org.gradle.api.artifacts.Configuration;
import org.gradle.api.file.ConfigurableFileCollection;
import org.gradle.api.provider.ListProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.provider.Provider;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;

public interface MCPBase extends PatcherBase {
    Property<@NotNull String> getMcpArtifact();
    Property<@NotNull String> getMcpVersion();
    Property<@NotNull String> getMcpPipeline();

    /// Additional maven repositories to resolve the MCPConfig artifact from, formatted as {@code name=url}.
    ListProperty<String> getRepositories();

    /// Processor tool to run on the patched sources before they are renamed, in maven coordinates.
    /// The tool must follow the {@code --input <jar> --output <jar>} contract, like {@code mcpcleanup}.
    Property<@NotNull String> getProcessor();

    Provider<File> getClasses();
    Provider<File> getClassesRaw();

    Configuration getDependencyConfiguration();
    Provider<List<String>> getDependencies();
    Provider<File> getExtra();
}
