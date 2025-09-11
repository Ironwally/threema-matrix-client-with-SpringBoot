package com.hka.intranet.messageprotocol.matrix.springboot.additional_matrix_java_sdk.module_instant_Messaging.room.messages;

/** Matrix m.emote message */
public class MessageEmote extends MessageBase {

  protected MessageEmote(Builder builder) {
    super(builder);
  }

  public static final class Builder extends MessageBase.Builder {

  public Builder(MessageBase.Builder base) {
      super(base);
      this.type = "m.emote";
    }

    // Required: The emote action to perform. Maybe make as enum of list of available emojis from list
    @Override public Builder body(String body) { super.body(body); return this; }
    @Override public Builder format(String format) { super.format(format); return this; }
    @Override public Builder formattedBody(String formattedBody) { super.formattedBody(formattedBody); return this; }
    @Override public Builder timestamp(long timestamp) { super.timestamp(timestamp); return this; }
    @Override public Builder id(long id) { super.id(id); return this; }
    @Override public MessageEmote build() { return new MessageEmote(this); }
  }
}
