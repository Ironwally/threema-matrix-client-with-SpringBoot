package com.hka.intranet.messageprotocol.matrix.springboot.additional_matrix_java_sdk.module_instant_Messaging.room.messages;

import java.util.List;

/** Simplified Matrix poll message (non-interactive placeholder) */
public class MessagePoll extends MessageBase {
  private final String question;
  private final List<String> options;

  protected MessagePoll(Builder builder) {
    super(builder);
    this.question = builder.question;
    this.options = List.copyOf(builder.answers);
  }

  public String question() { return question; }
  public List<String> options() { return options; }

  public static final class Builder extends MessageBase.Builder {
    private boolean hidden;
    private String question;
    private List<String> answers = List.of();

  public Builder(MessageBase.Builder base) {
      super(base);
      this.type = "m.poll";
    }

    public Builder hidden() { this.hidden = true; return this; }
    public Builder open() { this.hidden = false; return this; }
    public Builder question(String question) { this.question = question; return this; }
    public Builder answers(String[] answers) { this.answers = List.of(answers); return this; }

    @Override public Builder body(String body) { super.body(body); return this; }
    @Override public Builder format(String format) { super.format(format); return this; }
    @Override public Builder formattedBody(String formattedBody) { super.formattedBody(formattedBody); return this; }
    @Override public Builder timestamp(long timestamp) { super.timestamp(timestamp); return this; }
    @Override public Builder id(long id) { super.id(id); return this; }
    @Override public MessagePoll build() { return new MessagePoll(this); }
  }
}
