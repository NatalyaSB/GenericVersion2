package com.company;

import org.w3c.dom.ls.LSOutput;

import java.util.*;
import java.util.Scanner;

public class Main {

    public interface CountMap <T> {
        // добавляет элемент в этот контейнер.
        void add(T t);

        //Возвращает количество добавлений данного элемента
        int getCount(T t);

        //Удаляет элемент и контейнера и возвращает количество его добавлений(до удаления)
        int remove(T t);

        //количество разных элементов
        int size();

        //Добавить все элементы из source в текущий контейнер, при совпадении ключей,     суммировать значения
        void addAll(CountMap<T> source);

        //Вернуть java.util.Map. ключ - добавленный элемент, значение - количество его добавлений
        Map toMap();

        //Тот же самый контракт как и toMap(), только всю информацию записать в destination
        void toMap(Map destination);

        void showAll();
    }
    public static class CountMapIml <T> implements CountMap<T> {

        private HashMap<T, Integer> hashMap = new HashMap<>();;
        //отображение ключ - объект, значение - количество вхождений объекта
        // добавляет элемент в этот контейнер.
        @Override
        public void add(T t) {
            //если переданный объект не содержится в коллекции добавляем с 1
            //содержится, найдем и добавим  к количеству 1
            if (!hashMap.containsKey(t)) {
                hashMap.put(t, 1);
            } else {
                hashMap.replace(t, hashMap.get(t) + 1);
            }
        }
        @Override
        public int getCount(T t) {
            //если содежится то возвращаем кол-во вхождений
            if (hashMap.containsKey(t)) {
                return(hashMap.get(t));
            }
            else {
                return (0);
            }
        }
        @Override
        public int remove(T t) {
            //Удаляет элемент из контейнера и возвращает количество его добавлений(до удаления)
            if (hashMap.containsKey(t)) {
                return(hashMap.remove(t));
            }
            else {
                return (0);
            }
        }
        @Override
        public int size() {
            //количество разных элементов
            return(hashMap.size());
        }
        @Override
        public  void addAll(CountMap<T> source) {
            //Добавить все элементы из source в текущий контейнер, при совпадении ключей, суммировать значения
            Map source2 = source.toMap();
            Set<T> setKeys = source2.keySet();
            for(T t:setKeys)
            {
                int count = (int) source2.get(t);
                for(int j=1;j<=count;j++)
                    this.add((T)t);
            }
        };

        @Override
        public  Map toMap() {
            //Вернуть java.util.Map. ключ - добавленный элемент, значение - количество его добавлений
            Map map=new HashMap();
            map=(Map) hashMap.clone();
            return(map);
        }
        @Override
        public void toMap(Map destination) {
            //Тот же самый контракт как и toMap(), только всю информацию записать в destination
            destination=this.toMap();
            destination.forEach((key,value)-> System.out.print(key+":"+value + " "));
        }

        @Override
        public  void showAll() {
            //Добавить все элементы из source в текущий контейнер, при совпадении ключей,     суммировать значения
            hashMap.forEach((key,value)-> System.out.print(key+":"+value + " "));
            System.out.println("\n*****************************");
        }
    }
    public static class CollectionUtils {
        public static<T> void addAll(List<? extends T> source, List<? super T> destination) {
            destination.addAll(source);
        }

        public static<T> List newArrayList(List<? extends T> source) {
            List<? extends T> list=new ArrayList<>();
            return(list);
        }

        public static<T> int indexOf(List<? extends T> source, T t) {
            return (source.indexOf(t));
        }

        public static<T> List limit(List<? extends T> source, int size) {
            //возвращаем часть коллекции
            if (size<=source.size()) {
                return(source.subList(0,size));
            }
            return(source);
        }

        public static<T> void add(List<? super T> source, T t) {
            final boolean add = source.add(t);
            //return(source);
        }

        public static<T> void removeAll(List<? super T> removeFrom, List<? super T> c2) {
            removeFrom.removeAll(c2);
        }

        public static<T> boolean containsAll(List<? super T> c1, List<? super T> c2) {
            //true если первый лист содержит все элементы второго
            return c1.containsAll(c2);
        }

        public static<T> boolean containsAny(List<? extends T> c1, List<? extends T> c2) {
            //true если первый лист содержит хотя-бы 1 второго
            if (!c1.isEmpty() && !c2.isEmpty()) {
                for (T t : c1) {
                    if(c2.contains(t)){
                        return true;
                    }
                }
            }
            return false;
        }

        public static <T extends Comparable<T>> List<T> range(List<T> list, T min, T max) {
            //Возвращает лист, содержащий элементы из входного листа в диапазоне от min до max.
            // Элементы сравнивать через Comparable.
            // Прмер range(Arrays.asList(8,1,3,5,6, 4), 3, 6) вернет {3,4,5,6}
            List<T> dest = list.subList(0,0);//Создаем пустой список
            Iterator<T> it = list.iterator();
            while(it.hasNext()){
                T next = it.next();
                //System.out.println(next);
                if (next.compareTo(min) >= 0 && next.compareTo(max) <= 0){
                    System.out.println(next);
                    dest.add(next);
                }
             }
            return(dest);
        }

        public static <T> List range(List<T> list, T min, T max, Comparator<Object> comparator) {
            //Возвращает лист, содержащий элементы из входного листа в диапазоне от min до max.
            // Элементы сравнивать через Comparable.
            // Прмер range(Arrays.asList(8,1,3,5,6, 4), 3, 6) вернет {3,4,5,6}
            List<T> dest = list.subList(0,0);//Создаем пустой список
            for(T t : list){
                if(comparator.compare(t,min) >= 0 && comparator.compare(t,max) <= 0){
                    dest.add(t);
                }
            }
            return dest;
        }
    }

    public static void main(String[] args)
    {

        CountMap<Integer> map = new CountMapIml<>() ;
        CountMap<Integer> map2 = new CountMapIml<>() ;
        Map map3=new HashMap();
        Map map4=new HashMap();
        Map map5=new HashMap();
        map2.add(2);
        map2.add(3);
        map2.add(10);

        map.add(3);
        map.add(10);
        map.add(10);
        map.add(5);
        map.add(6);
        map.add(5);
        map.add(10);
        System.out.println("map");
        map.showAll();
        System.out.println("map2");
        map2.showAll();
        //Возвращает количество добавлений данного элемента
        System.out.println("Возвращает количество добавлений данного элемента 10  "+map.getCount(10));
        //map.getCount(10);
        //map.showAll();
        //Удаляет элемент и контейнера и возвращает количество его добавлений(до удаления)
        System.out.println("Удаляет элемент из контейнера и возвращает количество его добавлений(до удаления) 3 " + map.remove(3));
        //map.remove(3);
        map.showAll();
        //количество разных элементов
        System.out.println("количество разных элементов" +map.size());
        //map.size();
        map.showAll();
        //Добавить все элементы из source в текущий контейнер, при совпадении ключей,     суммировать значения
        System.out.println("Добавить все элементы из source в текущий контейнер, при совпадении ключей,     суммировать значения");
        map.addAll(map2);
        map2.showAll();
        map.showAll();
        //Вернуть java.util.Map. ключ - добавленный элемент, значение - количество его добавлений
        System.out.println("Вернуть java.util.Map. ключ - добавленный элемент, значение - количество его добавлений");
        map3=map.toMap();
        map3.forEach((key,value)-> System.out.print(key+":"+value + " "));
        System.out.println(" ");

        //Тот же самый контракт как и toMap(), только всю информацию записать в destination
        System.out.println("Тот же самый контракт как и toMap(), только всю информацию записать в destination");
        map2.toMap(map4);
        /*CountMap<String> map = new CountMapIml<>() ;
        CountMap<String> map2 = new CountMapIml<>() ;
        Map map3=new HashMap();
        Map map4=new HashMap();
        map2.add("2");
        map2.add("3");
        map2.add("10");

        map.add("3");
        map.add("10");
        map.add("10");
        map.add("5");
        map.add("6");
        map.add("5");
        map.add("10");
        System.out.println("map");
        map.showAll();
        System.out.println("map2");
        map2.showAll();
        //Возвращает количество добавлений данного элемента
        System.out.println("Возвращает количество добавлений данного элемента 10  "+map.getCount("10"));
        //map.getCount(10);
        //map.showAll();

        //Удаляет элемент и контейнера и возвращает количество его добавлений(до удаления)
        System.out.println("Удаляет элемент из контейнера и возвращает количество его добавлений(до удаления) 3 " + map.remove("3"));
        //map.remove(3);
        map.showAll();
        //количество разных элементов
        System.out.println("количество разных элементов" +map.size());
        //map.size();
        map.showAll();
        //Добавить все элементы из source в текущий контейнер, при совпадении ключей,     суммировать значения
        System.out.println("Добавить все элементы из source в текущий контейнер, при совпадении ключей,     суммировать значения");
        map.addAll(map2);
        map2.showAll();
        map.showAll();
        //Вернуть java.util.Map. ключ - добавленный элемент, значение - количество его добавлений
        System.out.println("Вернуть java.util.Map. ключ - добавленный элемент, значение - количество его добавлений");
        map3=map.toMap();
        map3.forEach((key,value)-> System.out.print(key+":"+value + " "));
        System.out.println(" ");

        //Тот же самый контракт как и toMap(), только всю информацию записать в destination
        System.out.println("Тот же самый контракт как и toMap(), только всю информацию записать в destination");
        map2.toMap(map4);*/
        List<Integer> b1 = new ArrayList<>(Arrays.asList(8,1,3,5,6, 4));
        CollectionUtils.add(b1,8);
        System.out.println("Test add: "+b1);



        CollectionUtils  colUtil = new CollectionUtils();
        List<Integer> listDestination = new ArrayList<>();
        List<Integer> listSource = new ArrayList<>(Arrays.asList(10,10,5,6,5,10));

        List<Integer> listSource2 = new ArrayList<>(Arrays.asList(1,0));
        System.out.println("listSource ");
        listSource.forEach((value)-> System.out.print(value + " "));
        System.out.println(" ");

         //limit
        listDestination=colUtil.limit(listSource,5);
        System.out.println("limit 5");
        listDestination.forEach((value)-> System.out.print(value + " "));
        System.out.println(" ");
        //indexOf
        System.out.println("indexOf 6 " +colUtil.indexOf(listDestination, 6));
        colUtil.add(listSource2,2);
        System.out.println("listSource2");
        listSource2.forEach((value)-> System.out.print(value + " "));
        //true если первый лист содержит все элементы второго
        System.out.println("containsAll  " +colUtil.containsAll( listSource, listSource2));

        //true если первый лист содержит хотя-бы 1 второго
        System.out.println("containsAny  " +colUtil.containsAny( listSource, listSource2));
        //Возвращает лист, содержащий элементы из входного листа в диапазоне от min до max.
        // Элементы сравнивать через Comparable.
        // Прмер range(Arrays.asList(8,1,3,5,6, 4), 3, 6) вернет {3,4,5,6}

        List<Integer> listDestination2 = new ArrayList<>();
        colUtil.range(b1, 3,6);

       }
}