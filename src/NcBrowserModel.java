import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//Попытка использовать паттерн MVC
public class NcBrowserModel {
    //Объявляем переменную для хранения данных поиска по списку программ, которые получаем в методе listNc
    public static ArrayList<Integer> contSearch;
    //Метод получает массив файлов и возвращает путь к файлам в виде строки
    public static String pathNc(File[] file) {
        StringBuilder oneP = new StringBuilder();
        for (int i = 0; i < file.length; i++){
            oneP.append(file[i].getAbsoluteFile() + " ");
        }
        String textPatch = oneP.toString();
        return textPatch;
    }
    //Метод получает массив файлов(текстовых) и возвращает в виде одной строки содержимое всех файлов
    public static String textOpenFiles(File[] files) {
        StringBuilder textOpFiBui = new StringBuilder();
        //Перебор массива файлов
        for (int i = 0; i < files.length; i++){
            try {
                FileReader reader = new FileReader(String.valueOf(files[i].toPath()));
                int c;
                //Посимпольное чтение каждого файла до тех пор, пока чтение возможно
                while ((c = reader.read())!=-1){
                    textOpFiBui.append((char) c);
                }
                /*
                Вариант для Java 11 - чтение всего файла сразу, т.е. не нужен вложенный цикл while для посимвольного чтения
                textOpFiBui.append(Files.readString(files[i].toPath()));
                */
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String textOpFi = textOpFiBui.toString();
        return  textOpFi;
    }
    //Метод получает текст всех программ в виде строки и возвращает массив строк, содержащий заголовки программ
    public static String[] listNc(String allText) {
        //Создаем содержащую регулярное выражение строку
        String  rgx = ValuesNcBrowser.REGEX_NAME;
        //Создаем экземпляры классов Pattern и Matcher
        Pattern pattern = Pattern.compile(rgx);
        Matcher matcher = pattern.matcher(allText);
        //Создаем динамические массивы для хранения заголовков программ и номеров позиций начала каждого заголовка в тексте программ
        ArrayList<String> listCnc = new ArrayList<>();
        contSearch = new ArrayList<>();
        //Перебираем текст до тех пор, пока поиск возможен
        //Переменная contSearch была объявлена в классе, а не методе для доступа к ней из другого класса, а метод уже возвращет массив заголовков
        while (matcher.find()){
            listCnc.add(matcher.group());
            contSearch.add(matcher.start());
        }
        return (String[]) listCnc.toArray(new String[0]);
    }
    //Метод получая текст программ и номера позиций извлекает подстроку (текст отдельной программы) и возвращает ее
    public static String textNc(String allText, int indexBegin, int indexEnd){
        //Извлечение подстроки
        StringBuilder textFormat = new StringBuilder(allText.substring(indexBegin, indexEnd));
        //Удаляем в конце программы лишние символы %, начала новой строки, пробелы и перевода каретки
        char percent = '%';
        char linefeed = '\n';
        char carriadereturn = '\r';
        char spase = ' ';
        while (textFormat.charAt(textFormat.length()-1)==percent
                | textFormat.charAt(textFormat.length()-1)==linefeed
                | textFormat.charAt(textFormat.length()-1)==carriadereturn
                | textFormat.charAt(textFormat.length()-1)==spase) {
            textFormat.deleteCharAt(textFormat.length()-1);
        }
        //Добавляем в начало и в конец программы знак %
        textFormat.insert(0, "%"+System.lineSeparator());
        textFormat.append(System.lineSeparator()+"%");
        //Приводим к типу String
        String bodyNc = textFormat.toString();
        return bodyNc;
    }
    //Метод возвращает массив порядковых номеров элементов в которых есть совпадения при поиске текста в строковом массиве(списке программ)
    public static ArrayList searchText(String textSearch, String[] listCnc) {
        //Создаем массив для хранения номеров позиций совпадений
        ArrayList resSearch = new ArrayList<>();
        int n = 0;
        //Перебор массива от 0 до размера массива
        for (int i = 0; i < listCnc.length; i++){
            //Если поиск действителен, то в массив позиций добавляется номер элемента строкового массива с совпадением
            if (listCnc[i].contains(textSearch)){
                resSearch.add(i);
                n++;
            }
        }
        return  resSearch;
    }
}