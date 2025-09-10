package com.hka.intranet.messageprotocol.matrix.springboot.additional_matrix_java_sdk.module_instant_Messaging.room.messages;

/** Matrix m.sticker message */
public class MessageSticker extends MessageBase {

  protected MessageSticker(Builder builder) {
    super(builder);
  }

  public static final class Builder extends MessageBase.Builder {

  public Builder(MessageBase.Builder base) {
      super(base);
      this.type = "m.sticker";
    }

    @Override public Builder body(String body) { super.body(body); return this; }
    @Override public Builder format(String format) { super.format(format); return this; }
    @Override public Builder formattedBody(String formattedBody) { super.formattedBody(formattedBody); return this; }
    @Override public Builder timestamp(long timestamp) { super.timestamp(timestamp); return this; }
    @Override public Builder id(long id) { super.id(id); return this; }
    @Override public MessageSticker build() { return new MessageSticker(this); }
  }
}
