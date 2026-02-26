package jp.co.abemasa.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import lombok.Data;

// 画面1から受け取る情報を保持する
@Data
public class EntryForm {

	//	@NotBlank(message = "⚠名前は必須です")
	//	private String fullName;

	//	@NotBlank(message = "⚠フリガナは必須です")
	//	private String kanaName;

	// 漢字姓
	@NotBlank(message = "⚠必須項目です")
	private String lastName;

	// 漢字名
	@NotBlank(message = "⚠必須項目です")
	private String firstName;

	// 漢字姓
	@NotBlank(message = "⚠必須項目です")
	private String kanaLastName;

	// 漢字姓
	@NotBlank(message = "⚠必須項目です")
	private String kanaFirstName;

	@NotBlank(message = "⚠必須項目です")
	private String grade;

	@NotEmpty(message = "⚠クラスを1つ以上選択してください")
	private String[] venue;
}
