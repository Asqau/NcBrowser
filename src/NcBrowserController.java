import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class NcBrowserController {
    //Объявляем переменные, которые будут использоваться в разных методах
    //Массив текстовых файлов с тексром программ
    private File[] files;
    //Текст, содержащийся во всех файлах
    private String textOpenFile;
    //Список программ, содержащихся в textOpenFile в виде массива строк
    private String[] listNc;
    //Номера позиций элементов в listNc, в которых есть совпадения при поиске текста
    private ArrayList<Integer> numberSearch;
    //Номер элемента списка в numberSearch
    private  int counter;
    //Метод отвечает за действия, вохникаюцие при нажатии на кнопку открытия файла (NcBrowserView.buttonOpen)
    public void clickButtonOpen() {
        //Создается слушатель, который реагирует на нажатие кнопки
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //Создается файловый диалог
                JFileChooser jFileChooser = new JFileChooser();
                //Устанавливаем папку, которая открывается при вызове файлового диалога
                File dir = new File("D:\\Java");
                jFileChooser.setCurrentDirectory(dir);
                //Устанавливаем заголовок окна файлового диалога
                jFileChooser.setDialogTitle(ValuesNcBrowser.SELECT_FILES);
                //Устанавливаем тип выбора - файл
                jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                //Устанавливается возможность множественного выбора
                jFileChooser.setMultiSelectionEnabled(true);
                //Отображаем файловый диалог и записываем результат работы с файловым диалогом в переменную res
                int res = jFileChooser.showOpenDialog(null);
                //Получаем массив файлов
                files = jFileChooser.getSelectedFiles();
                //Если файл (файлы) был выбран то выполняются действия внутри цикла
                if (res == JFileChooser.APPROVE_OPTION) {
                    //Отображаем путь к выбранным файлам в поле NcBrowserView.textPathFile
                    NcBrowserView.textPathFile.setText(NcBrowserModel.pathNc(files));
                    //Получаем текст всех файлов в виде строки при помощи метода textOpenFiles класса NcBrowserModel
                    textOpenFile = NcBrowserModel.textOpenFiles(files);
                    //Получаем список программ в виде массива строк при помощи метода listNc класса NcBrowserModel
                    listNc = NcBrowserModel.listNc(textOpenFile);
                    //Заполняем textListCnc полученным массивом
                    NcBrowserView.textListCnc.setListData(listNc);
                }
            }
        };
        //Добавляем слушатель к кнопке
        NcBrowserView.buttonOpen.addActionListener(actionListener);
    }
    //Метод отвечает за действия, вохникаюцие при нажатии на кнопку сохранения файла (NcBrowserView.buttonSaveAs)
    //Сохраняет полученный текст программы(или любой другой текст в поле NcBrowserView.textCnc) в указанный файл
    public void clickButtonSaveAs() {
        //Создается слушатель, который реагирует на нажатие кнопки
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //Создается файловый диалог
                JFileChooser jfsa = new JFileChooser();
                //Устанавливаем тип выбора - файл
                jfsa.setFileSelectionMode(JFileChooser.FILES_ONLY);
                //Заголовок диалога
                jfsa.setDialogTitle("Задать файл");
                //Отображаем файловый диалог и записываем результат работы с файловым диалогом в переменную res
                int res = jfsa.showSaveDialog(null);
                //Если файл был задан то выполняются действия внутри цикла
                if (res == JFileChooser.APPROVE_OPTION) {
                    //Получаем текст из поля NcBrowserView.textCnc
                    String textNc = NcBrowserView.textCnc.getText();
                    try {
                        //Записываем в указанный файл полученный текст
                        FileWriter fileWriter = new FileWriter(String.valueOf(jfsa.getSelectedFile()));
                        fileWriter.write(textNc);
                        fileWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        NcBrowserView.buttonSaveAs.addActionListener(actionListener);
    }
    //Метод отвечает за действия, вохникаюцие при вводе текста в поле поиска (NcBrowserView.textSearch)
    //Ищет совпадения по списку программ и выбирает первый элемент NcBrowserView.textListCnc, содержащий совпадение
    public void listenerSearch() {
        //Создается слушатель, который будет отслеживать изменения в поле поиска NcBrowserView.textSearch
        DocumentListener documentListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                warn();
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                warn();
            }

            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                warn();
            }
            //Метод, который выполняется при изменениях в поле поиска
            public void warn (){
                //Получаем текст который нужно найти
                String textSearch = NcBrowserView.textSearch.getText();
                //Получаем массив хранящий позиции listNc в которых есть совпадения
                numberSearch = NcBrowserModel.searchText(textSearch, listNc);
                //Если нет совпадений, то в поле NcBrowserView.textCnc выводится сообщение
                if (numberSearch.isEmpty()){
                    NcBrowserView.textCnc.setText(ValuesNcBrowser.NO_RESULT_OF_SEARCH);
                } else {
                    //Устанавливаем значение позиции поиска в начальное значение
                    counter = 0;
                    //Получаем позицию первого совпадения в поиске
                    int n = (int) numberSearch.get(counter);
                    //Выбираем элемент NcBrowserView.textListCnc имеющий первое совпадение
                    NcBrowserView.textListCnc.setSelectedIndex(n);
                    //Определяем позицию где находится элемент списка
                    Point point = new Point(0, n*NcBrowserView.textListCnc.getHeight()/listNc.length);
                    //Перематываем scrollingListCnc в полученную позицию
                    NcBrowserView.scrollingListCnc.getViewport().setViewPosition(point);
                }

            }
        };
        //Добавляется слушатель к полю поиска
        NcBrowserView.textSearch.getDocument().addDocumentListener(documentListener);
    }
    //Метод отвечает за действия, вохникаюцие при нажатии на кнопку дальнейшего поиска (NcBrowserView.buttonSearch)
    public void clickButtonSearch() {
        //Создается слушатель, который реагирует на нажатие кнопки
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //Выполняется при нажатии на кнопку
                //Выбираем следующее совпадение в массиве с результатами поиска
                counter = counter + 1;
                //Если полученное значение больше размера массива, то переходим в начало поиска
                if (counter > (numberSearch.size() - 1)){
                    counter = 0;
                }
                //Если совпадение при поиске одно, то переходим к первому и единственному элементу
                if (numberSearch.size() == 1){
                    counter = 0;
                }
                //Получаем позицию текущего совпадения в поиске
                int n = (int) numberSearch.get(counter);
                //Выбираем элемент NcBrowserView.textListCnc имеющий текущее совпадение
                NcBrowserView.textListCnc.setSelectedIndex(n);
                //Определяем позицию где находится элемент списка
                Point point = new Point(0, n*NcBrowserView.textListCnc.getHeight()/listNc.length);
                //Перематываем scrollingListCnc в полученную позицию
                NcBrowserView.scrollingListCnc.getViewport().setViewPosition(point);
            }
        };
        //Добавляем слушатель к кнопке
        NcBrowserView.buttonSearch.addActionListener(actionListener);
    }
    //Метод отвечает за действия, вохникаюцие при выборе элемента списка программ (NcBrowserView.textListCnc)
    //При выборе элемента списка textListCnc в поле textCnc отображается текст соответствующий программы
    public void selectListener() {
        //Создается слушатель который реагирует на выбор элемента списка
        ListSelectionListener listSelectionListener = new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                //Определяем выбранный элемент списка
                int clc = NcBrowserView.textListCnc.getSelectedIndex();
                //Определяем позицию первого символа текста программы в textOpenFile
                int indexBegin = NcBrowserModel.contSearch.get(clc);
                //Объявляем переменную для позиции последнего символа текста программы в textOpenFile
                int indexEnd;
                //Если программа последняя в списке то позиция последнего символа программы - последний символ всего textOpenFile
                if (clc == (listNc.length - 1)){
                    indexEnd = textOpenFile.length();
                } else {
                    //Иначе последняя позиция символа текста программы в textOpenFile - позиция начала следующей программы
                    indexEnd = NcBrowserModel.contSearch.get(clc+1);
                }
                //Зная позиции начала и конца программы получаем подстроку, содержащюю текст программы
                String cncText = NcBrowserModel.textNc(textOpenFile, indexBegin, indexEnd);
                //Вставляем текст программы в поле textCnc
                NcBrowserView.textCnc.setText(cncText);
                //Перематываем в начало
                NcBrowserView.textCnc.setCaretPosition(0);
            }
        };
        //Добавляем слушатель
        NcBrowserView.textListCnc.addListSelectionListener(listSelectionListener);
    }
}