package edu.vt.ward.survey;

public abstract class EditFormButtons {

  public static String getEditCopyDeleteButtonHTML (String editURL, String editAltText,
                                                String copyURL, String copyAltText,
                                                String deleteURL, String deleteAltText) {
    StringBuffer s = new StringBuffer ();

    s.append ( " <a href=\"" + editURL +"\" style=\"font-size:80%;color:#ffffff\">" + editAltText +"</a>\n" );
    s.append ( "<a href=\"" + copyURL +"\" style=\"font-size:80%;color:#ffffff\">" + copyAltText +"</a>\n" );
    s.append ( "<a href=\"" + deleteURL +"\" style=\"font-size:80%;color:#ffffff\">" + deleteAltText +"</a>\n" );
    return s.toString ();
  }

  public static String getAddAboveButtonsHTML ( String addQuestionAboveURL, String addTextAboveURL ) {
    StringBuffer s = new StringBuffer ();
    s.append ( " <a href=\"" + addQuestionAboveURL +"\" style=\"font-size:80%;color:#ffffff\">"+"add question above"+"</a>\n" );
    s.append ( "<a href=\"" + addTextAboveURL +"\" style=\"font-size:80%;color:#ffffff\">"+"add text above"+"</a>\n" );
    return s.toString ();
  }

  public static String getUpButtonHTML ( String URL, String altText ) {
    return ( " <a href=\"" + URL + "\" style=\"font-size:80%;color:#ffffff\">" + altText + "</a>\n" );
  }

  public static String getDownButtonHTML ( String URL, String altText ) {
    return ( "<a href=\"" + URL + "\" style=\"font-size:80%;color:#ffffff\">" + altText + "</a>\n" );
  }

}