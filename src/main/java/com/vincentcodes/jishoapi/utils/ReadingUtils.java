package com.vincentcodes.jishoapi.utils;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class ReadingUtils {
    private static Set<String> HIRAGANA_LETTERS = Arrays.stream("あ い う え お か き く け こ きゃ きゅ きょ さ し す せ そ しゃ しゅ しょ た ち つ て と ちゃ ちゅ ちょ な に ぬ ね の にゃ にゅ にょ は ひ ふ へ ほ ひゃ ひゅ ひょ ま み む め も みゃ みゅ みょ や ゆ よ ら り る れ ろ りゃ りゅ りょ わ ゐ ゑ を が ぎ ぐ げ ご ぎゃ ぎゅ ぎょ ざ じ ず ぜ ぞ じゃ じゅ じょ だ ぢ づ で ど ぢゃ ぢゅ ぢょ ば び ぶ べ ぼ びゃ びゅ びょ ぱ ぴ ぷ ぺ ぽ ぴゃ ぴゅ".split(" ")).collect(Collectors.toSet());
    private static Set<String> KATAGANA_LETTERS = Arrays.stream("ア イ ウ エ オ カ キ ク ケ コ キャ キュ キョ サ シ ス セ ソ シャ シュ ショ タ チ ツ テ ト チャ チュ チョ ナ ニ ヌ ネ ノ ニャ ニュ ニョ ハ ヒ フ ヘ ホ ヒャ ヒュ ヒョ マ ミ ム メ モ ミャ ミュ ミョ ヤ ユ ヨ ラ リ ル レ ロ リャ リュ リョ ワ ヰ ヱ ヲ ガ ギ グ ゲ ゴ ギャ ギュ ギョ ザ ジ ズ ゼ ゾ ジャ ジュ ジョ ダ ヂ ヅ デ ド ヂャ ヂュ ヂョ バ ビ ブ ベ ボ ビャ ビュ ビョ パ ピ プ ペ ポ ピャ ピュ".split(" ")).collect(Collectors.toSet());

    public static boolean isHiragana(String s){
        return HIRAGANA_LETTERS.contains(s);
    }

    public static boolean isKatana(String s){
        return KATAGANA_LETTERS.contains(s);
    }
}
