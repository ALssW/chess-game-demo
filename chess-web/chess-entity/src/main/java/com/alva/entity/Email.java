package com.alva.entity;

/**
 * @author ALsW
 * @version 1.0.0
 * @since 2023-02-15
 */
public class Email {

    private String subject;
    private String to;
    private String content;

	public Email() {
	}

	public Email(String subject, String to, String content) {
		this.subject = subject;
		this.to = to;
		this.content = content;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
