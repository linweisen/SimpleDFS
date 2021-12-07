package org.simpledfs.core.command;

import com.beust.jcommander.Parameter;

public class Command {

    @Parameter(names={"--file", "-f"}, help = true, required =true, description = "file path")
    private String file;

    @Parameter(names = "--help", help = true)
    private boolean help;

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public boolean isHelp() {
        return help;
    }

    public void setHelp(boolean help) {
        this.help = help;
    }
}
