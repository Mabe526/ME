package jp.co.abemasa.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import lombok.Data;

// 画面1から受け取る情報を保持する
@Data
public class EntryForm {

	// 漢字姓
	@NotBlank(message = "⚠必須項目です")
	private String lastName;

	// 漢字名
	@NotBlank(message = "⚠必須項目です")
	private String firstName;

	// ふりがな姓
	@NotBlank(message = "⚠必須項目です")
	private String kanaLastName;

	// ふりがな名
	@NotBlank(message = "⚠必須項目です")
	private String kanaFirstName;

	// 学年
	@NotBlank(message = "⚠必須項目です")
	private String grade;

	// 会場
	@NotEmpty(message = "⚠クラスを1つ以上選択してください")
	private String[] venue;
}
