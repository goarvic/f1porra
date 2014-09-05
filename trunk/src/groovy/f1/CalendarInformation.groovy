package f1

import f1porra.Wget

/**
 * Created with IntelliJ IDEA.
 * User: vickop
 * Date: 15/04/14
 * Time: 21:50
 * To change this template use File | Settings | File Templates.
 */
class CalendarInformation {

    def htmlData


    CalendarInformation(String url)
    {
        Wget httpGetter = new Wget();
        htmlData = httpGetter.get(url);
    }



}
