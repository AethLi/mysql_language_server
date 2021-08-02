package cn.aethli.mls.converter;

/** @author SelcaNyan */
public interface Converter<T> {
  T valueOf(String src);

  String parse(Object o);
}
