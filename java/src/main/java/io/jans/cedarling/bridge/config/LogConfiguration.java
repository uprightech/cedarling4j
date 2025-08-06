/*
 * Janssen Project software is available under the Apache License (2004). See http://www.apache.org/licenses/ for full text.
 *
 * Copyright (c) 2025, Janssen Project
 */

package io.jans.cedarling.bridge.config;

/**
 * Logging configuration 
 * <p>
 *   Cedarling logging can be configured using this class. Except for logging
 *   via jans lock, which is enabled here, but configured in the bootstrap configuration directly.
 * </p>
 */
public class LogConfiguration {
    
    private LogType logType;
    private LogLevel logLevel;
    private MemoryLogConfiguration memoryLogConfiguration;

    /**
     * Default constructor
     * <p>
     *   Logging is turned off by default and the log level is set to {@code INFO}
     *   This should be used if this won't be a memory logging configuration
     * </p>
     */
    public LogConfiguration() {

        logType  = LogType.OFF;
        logLevel = LogLevel.INFO;
        memoryLogConfiguration = null;
    }
    
    /**
     * Constructor specifying the log type and the logging level
     * <p>This should be used for any logging configuration but memory logging</p>
     * @param logType log type 
     * @param logLevel log level 
     */
    public LogConfiguration(final LogType logType, final LogLevel logLevel) {

        this.logType = logType;
        this.logLevel = logLevel;

    }

    /**
     * Constructor specifying the log type , level and memory logging configuration to use
     * <p>This should be used for memory logging</p>
     * @param logType the log type 
     * @param logLevel the logging level 
     * @param memoryLogConfiguration the memory logging configuration 
     */
    public LogConfiguration(final LogType logType, final LogLevel logLevel, final MemoryLogConfiguration memoryLogConfiguration) {

        this.logType =  logType;
        this.logLevel = logLevel;
        this.memoryLogConfiguration = memoryLogConfiguration;
    }

    /**
     * Gets the log type used for logging 
     * @return the log type 
     */
    public LogType getLogType() {

        return logType;
    }

    /**
     * Specifies the log type used for logging
     * @param logType the log type
     * @return the current instance of this configuration 
     */
    public LogConfiguration setLogType(LogType logType) {

        this.logType = logType;
        return this;
    }

    /**
     * Gets the memory logging configuration in case memory logging is used
     * @return the memory logging configuration to be used for logging
     */
    public MemoryLogConfiguration getMemoryLogConfiguration() {

        return memoryLogConfiguration;
    }

    /**
     * Specifies the memory logging configuration in case memory logging is used.
     * <p>This only takes effect if {@code logType} is set to MEMORY</p>
     * @param memoryConfiguration
     * @return the current instance of this configuration
     */
    public LogConfiguration setMemoryConfiguration(MemoryLogConfiguration memoryConfiguration) {

        this.memoryLogConfiguration = memoryLogConfiguration;
        return this;
    }

    /**
     * Gets the log level to use for log messages 
     * @return  the log level 
     */
    public LogLevel getLogLevel() {

        return logLevel;
    }

    /**
     * Specifies the log level to use for log messages 
     * @param loglevel the log level 
     * @return the current
     */
    public LogConfiguration setLogLevel(LogLevel loglevel) {

        this.logLevel = loglevel;
        return this;
    }

    /**
     * Creates a logging configuration with logging disabled
     * @return an initialized logging configuration with logging disabled
     */
    public static LogConfiguration  noLogging() {

        return new LogConfiguration(LogType.OFF,LogLevel.INFO,null);
    }

    /**
     * Creates configuration to log messages to memory
     * @param memory_log_config memory logging configuration 
     * @param log_level logging level 
     * @return an initialized logging configuration that logs to memory
     */
    public static LogConfiguration memory(MemoryLogConfiguration memory_log_config, LogLevel log_level) {

        return new LogConfiguration(LogType.MEMORY,log_level,memory_log_config);
    }

    /**
     * Creates configuration to log messages to stdout
     * @param log_level logging level
     * @return an initialized logging configuration that logs to stdout
     */
    public static LogConfiguration stdOut(LogLevel log_level) {

        return new LogConfiguration(LogType.STDOUT,log_level,null);
    }

    /**
     * Creates configuration to log messages to Jans lock
     * @param log_level the logging level 
     * @return an initialized loggign configuration that logs to the jans lock server
     */
    public static LogConfiguration lock(LogLevel log_level) {

        return new LogConfiguration(LogType.LOCK,log_level,null);
    }
}
