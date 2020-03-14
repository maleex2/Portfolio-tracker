package Model;

class NoSuchTickerException extends Exception {
  NoSuchTickerException() {
  }

  NoSuchTickerException(String s) {
    super(s);
  }
}