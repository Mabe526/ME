package jp.co.abemasa.controller;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import jp.co.abemasa.entity.MClassEntity;
import jp.co.abemasa.entity.TEntryEntity;
import jp.co.abemasa.form.EntryForm;
import jp.co.abemasa.form.ParticipateForm;
import jp.co.abemasa.repository.MClassRepository;
import jp.co.abemasa.repository.TEntryRepository;

@Controller
public class HomeController {

	@Autowired
	MClassRepository mClassRepository;
	@Autowired
	private TEntryRepository tEntryRepository;

	// HOME画面へ接続
	@GetMapping("/home")
	public String getHome() {
		// ホーム画面の情報を返す
		return "html/home";
	}

	@PostMapping("/home")
	public String redirectHome() {
		return "redirect:/home";
	}

	// スクール生情報入力画面へ接続
	@PostMapping("/ent001")
	public ModelAndView input(ModelAndView mav) {
		// 画面情報を登録
		mav.setViewName("html/ent001");
		// 情報格納用のインスタンス生成
		mav.addObject("entryForm", new EntryForm());
		// ホーム画面の情報を返す
		return mav;
	}

	// 参加意思確認画面へ接続
	@GetMapping("/ent002")
	public ModelAndView intent(@ModelAttribute @Validated EntryForm form, BindingResult result, ModelAndView mav,
			HttpSession session) {

		// バリデーションチェック
		if (result.hasErrors()) {
			mav.setViewName("html/ent001");
			mav.addObject("entryForm", form);
			return mav;
		}

		// ①入力された情報を保持する
		session.setAttribute("EntryForm", form);

		// ② 検索条件
		String grade = form.getGrade();
		List<String> venueList = Arrays.asList(form.getVenue());

		// ③ DB検索
		List<MClassEntity> selectedClass = mClassRepository.findByVenueNameInAndGrade(venueList, grade);

		// ④セッションに保存
		session.setAttribute("selectedClass", selectedClass);

		// ⑤画面情報を登録
		mav.addObject("selectedClass", selectedClass);
		// 情報格納用のインスタンス生成
		mav.addObject("participateForm", new ParticipateForm());

		mav.setViewName("html/ent002");

		// ホーム画面の情報を返す
		return mav;
	}

	// 入力情報確認画面へ接続
	@PostMapping("/ent003")
	public ModelAndView confirm(@ModelAttribute @Validated ParticipateForm form, BindingResult result, ModelAndView mav,
			HttpSession session) {
		// 入力された情報を保持する
		session.setAttribute("participateForm", form);
		// セッション情報を取得
		EntryForm entryForm = (EntryForm) session.getAttribute("EntryForm");

		// 条件付きチェック
		if ("参加".equals(form.getParticipate())) {
			if (form.getChooseClass() == null || form.getChooseClass().length == 0) {
				result.rejectValue(
						"chooseClass",
						"NotEmpty",
						"⚠クラスを1つ以上選択してください");
			}
		}

		// セッションに登録してあったDB検索結果を再取得
		@SuppressWarnings("unchecked")
		List<MClassEntity> selectedClass = (List<MClassEntity>) session.getAttribute("selectedClass");
		// セッション情報を画面へ渡す
		mav.addObject("entryForm", entryForm);
		mav.addObject("participateForm", form);
		mav.addObject("selectedClass", selectedClass);

		// バリデーションチェック
		if (result.hasErrors()) {
			mav.setViewName("html/ent002"); // 入力画面
			mav.addObject("participateForm", form);
			mav.addObject("selectedClass", selectedClass);
			return mav;
		}

		// 画面情報を登録
		mav.setViewName("html/ent003");
		// ホーム画面の情報を返す
		return mav;
	}

	// 申し込み完了画面へ接続
	@PostMapping("/end")
	public ModelAndView complete(ModelAndView mav, HttpSession session) {
		// ent001とent002で取得したセッション情報を取得
		EntryForm entryForm = (EntryForm) session.getAttribute("EntryForm");
		ParticipateForm participateForm = (ParticipateForm) session.getAttribute("participateForm");

		// 申込を1レコードずつ登録するため、配列型の変数に情報を格納する
		String[] venues = entryForm.getVenue();
		String[] participatingClass = participateForm.getChooseClass();

		// 不参加の場合、1件のみレコードを登録する
		if ("不参加".equals(participateForm.getParticipate())) {
			// 不参加：1レコードだけ登録
			for (String venue : venues) {

				TEntryEntity entity = new TEntryEntity();
				entity.setFullName(entryForm.getFullName());
				entity.setKanaName(entryForm.getKanaName());
				entity.setGrade(entryForm.getGrade());
				entity.setVenue(venue);
				entity.setParticipate(participateForm.getParticipate());
				entity.setParticipatingClass(null); // クラスなし
				entity.setInsertDatetime(LocalDateTime.now());

				tEntryRepository.save(entity);
			}
		} else {
			// 会場 × クラス分 登録
			for (String venue : venues) {
				for (String chooseClass : participatingClass) {

					TEntryEntity entity = new TEntryEntity();
					entity.setFullName(entryForm.getFullName());
					entity.setKanaName(entryForm.getKanaName());
					entity.setGrade(entryForm.getGrade());
					entity.setVenue(venue);
					// debag
					System.out.println(venue);
					entity.setParticipate(participateForm.getParticipate());
					entity.setParticipatingClass(chooseClass);
					entity.setInsertDatetime(LocalDateTime.now());

					tEntryRepository.save(entity);
				}
			}
		}
		// セッション破棄
		session.invalidate();
		// 画面情報を登録
		mav.setViewName("html/end");
		// ホーム画面の情報を返す
		return mav;
	}
}
