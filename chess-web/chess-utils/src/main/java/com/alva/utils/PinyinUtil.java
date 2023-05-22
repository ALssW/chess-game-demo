package com.alva.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * @author ALsW
 * @version 1.0.0
 * @since 2023-02-09
 */
public class PinyinUtil {

	private static final HanyuPinyinOutputFormat FORMAT = new HanyuPinyinOutputFormat();


	public static String hanzi2Pinyin(String hanzi) {
		String result = hanzi;
		try {
			FORMAT.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
			result = PinyinHelper.toHanYuPinyinString(hanzi, FORMAT);
		} catch (BadHanyuPinyinOutputFormatCombination ignored) {
		}
		return result;
	}

}
