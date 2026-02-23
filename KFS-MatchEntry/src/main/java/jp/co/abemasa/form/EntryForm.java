package jp.co.abemasa.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import lombok.Data;

// formから受け取る情報を保持する
@Data
public class EntryForm {
	
	@NotBlank(message = "⚠名前は必須です")
	private String fullName;
	
	@NotBlank(message = "⚠フリガナは必須です")
	private String kanaName;
	
	@NotBlank(message = "⚠学年は必須です")
	private String grade;
	
	@NotEmpty(message = "⚠クラスを1つ以上選択してください")
	private String[] venue;
}
