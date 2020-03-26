import javax.swing.*;
import java.awt.*;

class NcBrowserView extends JFrame {
    //Создается текстовое поле для отображения пути к файлу
    static JTextField textPathFile = new JTextField("",40);
    //Создается список для вывода названий программ
    static  JList<String> textListCnc = new JList<String>();
    //Создается текстовое поле для вывода текста программы
    static JTextArea textCnc = new JTextArea(ValuesNcBrowser.DEFAULT_TEXT_CNC);
    // Поле ввода текста для поиска
    static JTextField textSearch = new JTextField("", 20);
    //Кнопка сохранения текста из поля textCnc в указанный файл
    static JButton buttonSaveAs = new JButton(ValuesNcBrowser.BUTTON_SAVEAS);
    //Кнопка открытия пути к файлу
    static JButton buttonOpen = new JButton(ValuesNcBrowser.BUTTON_OPEN);
    //Кнопка поиска по списку программ
    static JButton buttonSearch = new JButton(ValuesNcBrowser.BUTTON_SERCH);

    //Текстовые поля помещаются в поля прокрутки
    //JScrollPane scrollingPathFile = new JScrollPane(textPathFile);
    static JScrollPane scrollingListCnc = new JScrollPane(textListCnc);
    JScrollPane scrollingCnc = new JScrollPane(textCnc);
    //Создается панель для размещения textPathFile и buttonPath
    JPanel panelHead = new JPanel();
    //Создается панель для размещения кнопок buttonRun и buttonSaveAs
    JPanel panelButton = new JPanel();

    public NcBrowserView() {
        //Вызываем экземпляр родительского класса JFame и устанавливаем заголовок фрейма (окна)
        super(ValuesNcBrowser.TITLE_WINDOW);
        //Устанавливаем координаты расположения и размеры фрейма
        setBounds(50, 1, 600, 600);
        //Задаем поведение контейнера при нажатии пользователя на кнопку выхода (close). Используется метод системного выхода
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Извлекаем из фрейма  контейнер
        Container container = getContentPane();
        //Наполняем панели и контейнер
        panelHead.add(textPathFile);
        panelHead.add(buttonOpen);
        container.add(panelHead, BorderLayout.NORTH);
        container.add(scrollingListCnc, BorderLayout.WEST);
        container.add(scrollingCnc, BorderLayout.CENTER);
        panelButton.add(textSearch);
        panelButton.add(buttonSearch);
        panelButton.add(buttonSaveAs);
        container.add(panelButton, BorderLayout.SOUTH);
    }
}