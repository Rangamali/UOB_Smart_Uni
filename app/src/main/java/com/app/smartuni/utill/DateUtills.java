package com.app.smartuni.utill;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtills {
  /**
   * DATE_FORMAT_1 = hh:mm a
   * The output will be -: 10:37 am
   *
   * DATE_FORMAT_2 = h:mm a
   * Output will be -: 10:37 am
   *
   * DATE_FORMAT_3 = yyyy-MM-dd
   * The output will be -: 2018-12-05
   *
   * DATE_FORMAT_4 = dd-MMMM-yyyy
   * The output will be -: 05-December-2018
   *
   * DATE_FORMAT_5 = dd MMMM yyyy
   * The output will be -: 05 December 2018
   *
   * DATE_FORMAT_6 = dd MMMM yyyy zzzz
   * The output will be -: 05 December 2018 UTC
   *
   * DATE_FORMAT_7 = EEE, MMM d, ''yy
   * The output will be -: Wed, Dec 5, '18
   *
   * DATE_FORMAT_8 = yyyy-MM-dd HH:mm:ss
   * The Output will be -: 2018-12-05 10:37:43
   *
   * DATE_FORMAT_9 = h:mm a dd MMMM yyyy
   * The output will be -: 10:37 am 05 December 2018
   *
   * DATE_FORMAT_10 = K:mm a, z
   * The output will be -: 10:37 am, UTC
   *
   * DATE_FORMAT_11 = hh 'o''clock' a, zzzz
   * The output will be -: 10 o'clock am, UTC
   *
   * DATE_FORMAT_12 = yyyy-MM-dd'T'HH:mm:ss.SSS'Z'
   * The output will be -: 2018-12-05T10:37:43.937Z
   *
   * DATE_FORMAT_13 = E, dd MMM yyyy HH:mm:ss z
   * The output will be -: Wed, 05 Dec 2018 10:37:43 UTC
   *
   * DATE_FORMAT_14 = yyyy.MM.dd G 'at' HH:mm:ss z
   * The output will be -: 2018.12.05 AD at 10:37:43 UTC
   *
   * DATE_FORMAT_15 = yyyyy.MMMMM.dd GGG hh:mm aaa
   * The output will be -: 02018.D.05 AD 10:37 am
   *
   * DATE_FORMAT_16 = EEE, d MMM yyyy HH:mm:ss Z
   * The output will be -: Wed, 5 Dec 2018 10:37:43 +0000
   *
   * DATE_FORMAT_17 = yyyy-MM-dd'T'HH:mm:ss.SSSZ
   * The output will be -: 2018-12-05T10:37:43.946+0000
   *
   * DATE_FORMAT_18 = yyyy-MM-dd'T'HH:mm:ss.SSSXXX
   * The output will be -: 2018-12-05T10:37:43.949Z
   *
   * DATE_FORMAT_19 = dd-MMM-yyyy
   */

  public static String YYYY_MM_DD = "yyyy-MM-dd";
  public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

  public static String getCalenderFormatted(Calendar mCalendar, String mFormat) {
    SimpleDateFormat format = new SimpleDateFormat(mFormat);
    return format.format(mCalendar.getTime());
  }
}

