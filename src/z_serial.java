package ais.zero.z_serial;

import com.fazecast.jSerialComm.SerialPort;
import java.io.IOException;

public class z_serial {
  // Fields
  // sp: com.fazecast.jSerialComm.SerialPort.SerialPort Object
  private SerialPort sp;
  // logs_handler: Object From Log Class That Handles System Logs
  private log logs_handler;
  // Constructor
  // @param port - Serial Port Name
  // @param rate - Serial Port Baud Rate
  // @param pin1 - Serial Pin #1
  // @param pin2 - Serial Pin #2
  // @param _debug - Enable/Disable Debug Logs
  public z_serial(String port, int rate, int pin1, int pin2, boolean _debug, serial_read_event_handler handler) {
    this.logs_handler = new log(_debug);
    this.logs_handler.verbose(log.LOG_INFO, "Initlizing Serial Communication With " + port + "...");
    try {
      this.sp = SerialPort.getCommPort(port);
      this.sp.setComPortParameters(rate, 8, pin1, pin2);
      this.sp.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
      if (sp.openPort()) {
        this.logs_handler.verbose(log.LOG_INFO, "<PORT:" + port + "> Connection Established");
        this.logs_handler.verbose(log.LOG_INFO, "Starting Async Event Loop...");
        new event_loop(this.sp.getInputStream(), handler, this.logs_handler).start();
        this.logs_handler.verbose(log.LOG_INFO, "Async Event Loop Started");
      } else {
        this.logs_handler.verbose(log.LOG_ERROR, "<PORT:" + port + "> Connection Refused");
      }
    } catch (Exception e) {
      this.logs_handler.verbose(log.LOG_ERROR, e.getMessage());
    }
  }
  // Properties
  // Debug Mode Getter - Setter
  public boolean get_debug_state() {
    return this.logs_handler.debug;
  }
  public void set_debug_state(boolean new_state) {
    this.logs_handler.debug = new_state;
  }
  // Methods
  // Write Data to The Output Stream
  // @param data - The Data to be Written
  // @return - [true: Sucess, false: faild]
  public boolean write_byte(byte data) {
    try {
      this.sp.getOutputStream().write(data);
      this.sp.getOutputStream().flush();
      this.logs_handler.verbose(log.LOG_INFO, "<Data: " + ((int)data) + "> Sent");
      return true;
    } catch (IOException e) {
      this.logs_handler.verbose(log.LOG_ERROR, e.getMessage());
      return false;
    }
  }
  // Destructor
  protected void finalize() {
    this.logs_handler.verbose(log.LOG_INFO, "Closing Serial Port...");
    if (this.sp.closePort()) {
      this.logs_handler.verbose(log.LOG_INFO, "Serial Port Closed");
    } else {
      this.logs_handler.verbose(log.LOG_ERROR, "Can't Close Serial Port");
    }
  }
}
