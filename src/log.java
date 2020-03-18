package ais.zero.z_serial;

// Log class
public class log {
  // Constants
  public static final byte LOG_ERROR = 0;
  public static final byte LOG_INFO = 1;
  // Fields
  // Enable Debugging Flag
  public boolean debug;
  // Constructor
  // @param _debug - Enable Debugging Flag
  public log(boolean _debug) {
    this.debug = _debug;
  }
  // Methods
  // verbose: Print Logs to The Screen
  // @param tag_code - Code Represents The Type of The Log Message
  // @param log_msg - The Actual Log Message
  public void verbose(byte tag_code, String log_msg) {
    if (this.debug) {
      String tag = (tag_code == 0) ? "[ERROR]: " : "[INFO]: ";
      System.out.println(tag + log_msg);
    }
  }
}
