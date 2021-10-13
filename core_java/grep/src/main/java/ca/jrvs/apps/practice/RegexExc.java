package ca.jrvs.apps.practice;

public interface RegexExc {

  /**
   * return true if filename extension is jpg or jpeg (case sensitive)
   * @param filename
   * @return
   */
  public boolean matchJpeg(String filename);

  /**
   * return true if IP is valid
   * to simplify the problem, IP address range is between 0.0.0.0 and 999.999.999.999
   * @param ip
   * @return
   */
  public boolean matchIP(String ip);

  /**
   * return true if line is empty (eg empty, whitespace, tabs, etc.)
   * @param line
   * @return
   */
  public boolean isEmptyLine(String line);
}