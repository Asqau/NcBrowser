//Стартовый класс
public class MainApp {
    public MainApp() {

    }
    public static void main (String args[]){
        //Создаются объекты всех классов кроме класса с константами ValuesNcBrowser и вызываются методы класса NcBrowserController
        NcBrowserView ncBrowserView = new NcBrowserView();
        NcBrowserModel ncBrowserModel = new NcBrowserModel();
        NcBrowserController ncBrowserController = new NcBrowserController();
        ncBrowserController.clickButtonOpen();
        ncBrowserController.clickButtonSaveAs();
        ncBrowserController.clickButtonSearch();
        ncBrowserController.selectListener();
        ncBrowserController.listenerSearch();
        //Делаем видимым экземпляр класса, наследованный от JFrame
        ncBrowserView.setVisible(true);
    }
}