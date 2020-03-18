package ais.zero.z_serial;

import java.lang.Thread;
import java.io.InputStream;

class event_loop extends Thread {
  // Fields
  // Serial Input Stream Object
  InputStream in;
  // On Read Event Handler
  serial_read_event_handler handler;
  // System's Los Manager
  log main_log;
  // Constructor
  public event_loop(InputStream _in, serial_read_event_handler _hander, log _main_log) {
    this.in = _in;
    this.handler = _hander;
    this.main_log = _main_log;
  }
  // Async Task
  public void run() {
    while (true) {
      try {
        if (this.in.available() > 0) {
          String data = "";
          int c = 0;
          c = this.in.read();
          while (c != 10) {
            data += Character.toString((char)c);
            c = this.in.read();
          }
          this.handler.execute(data);
          Thread.sleep(100);
        }
      } catch (Exception e) {
        this.main_log.verbose(log.LOG_ERROR, e.getMessage());
      }
    }
  }
}
