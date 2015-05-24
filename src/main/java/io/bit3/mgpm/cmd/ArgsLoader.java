package io.bit3.mgpm.cmd;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.io.File;

public class ArgsLoader {
  private final OptionsFactory optionsFactory;

  public ArgsLoader() {
    this(new OptionsFactory());
  }

  public ArgsLoader(OptionsFactory optionsFactory) {
    this.optionsFactory = optionsFactory;
  }

  public Args load(String[] cliArguments) {
    Args args = new Args();
    Options options = optionsFactory.create();

    DefaultParser parser = new DefaultParser();
    try {
      CommandLine cmd = parser.parse(options, cliArguments);

      if (cmd.hasOption(OptionsFactory.HELP_OPT)) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("mgpm", options, true);
        return null;
      }

      if (cmd.hasOption(OptionsFactory.CONFIG_OPT)) {
        args.setConfig(new File(cmd.getOptionValue('c')));
      }

      if (cmd.hasOption(OptionsFactory.LIST_OPT)) {
        args.setDoList(true);
      }

      if (cmd.hasOption(OptionsFactory.INIT_OPT)) {
        args.setDoInit(true);
      }

      if (cmd.hasOption(OptionsFactory.STAT_OPT)) {
        args.setDoStat(true);
      }

      if (cmd.hasOption(OptionsFactory.UPDATE_OPT)) {
        args.setDoUpdate(true);
      }

      if (cmd.hasOption(OptionsFactory.GUI_OPT)) {
        args.setShowGui(true);
      }

      if (cmd.hasOption(OptionsFactory.VERY_VERBOSE_OPT)) {
        args.setLoggerLevel("debug");
      } else if (cmd.hasOption(OptionsFactory.VERBOSE_OPT)) {
        args.setLoggerLevel("info");
      } else if (cmd.hasOption(OptionsFactory.QUIET_OPT)) {
        args.setLoggerLevel("error");
      } else {
        args.setLoggerLevel("warn");
      }

      return args;
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
  }
}