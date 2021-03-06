package br.com.caelum.tubaina.parser.html.desktop;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.caelum.tubaina.Book;
import br.com.caelum.tubaina.Chapter;
import br.com.caelum.tubaina.parser.Parser;
import br.com.caelum.tubaina.template.FreemarkerProcessor;
import br.com.caelum.tubaina.util.HtmlSanitizer;
import freemarker.template.Configuration;

public class ChapterToString {

	private final Parser parser;

	private final Configuration cfg;

	private final List<String> dirTree;

    private final List<String> ifdefs;

	public ChapterToString(final Parser parser, final Configuration cfg, final List<String> dirTree, List<String> ifdefs) {
		this.parser = parser;
		this.cfg = cfg;
		this.dirTree = dirTree;
        this.ifdefs = ifdefs;
	}

	public StringBuffer generateChapter(final Book b, final Chapter c, final int index, final int currentDir) {
		Map<String, Object> map = new HashMap<String, Object>();
		for (String ifdef : ifdefs) {
            map.put(ifdef, true);
        }
		map.put("book", b);
		map.put("chapter", c);
		map.put("curchap", index);
		map.put("curdir", currentDir);
		map.put("parser", parser);
		map.put("dirTree", dirTree);
		map.put("sanitizer", new HtmlSanitizer());
		map.put("previous", dirTree.get(currentDir - 1));
		if (currentDir+1 < dirTree.size()) {
			map.put("next", dirTree.get(currentDir + 1));	
		} else {
			map.put("next", "");
		}
		return new FreemarkerProcessor(cfg).process(map, "chapter.ftl");
	}
	
	public StringBuffer generateKindleHtmlChapter(Book book, Chapter chapter, StringBuffer allSectionsContent) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("book", book);
        map.put("chapter", chapter);
        map.put("allSectionsContent", allSectionsContent);
        map.put("parser", parser);
        map.put("sanitizer", new HtmlSanitizer());
        return new FreemarkerProcessor(cfg).process(map, "chapter.ftl");
    }

}
