package cn.aethli.mls.converter;

/** @author SelcaNyan */
public class StringConverter implements Converter<String> {

  @Override
  public String valueOf(String src) {
    return src;
  }

  @Override
  public String parse(Object o) {
    return o.toString();
  }
}
