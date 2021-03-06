package br.com.caelum.tubaina.builder.replacer;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.TubainaException;
import br.com.caelum.tubaina.chunk.CenteredParagraphChunk;

public class CenteredParagraphReplacerTest {
	private CenteredParagraphReplacer replacer;
	private List<Chunk> chunks;

	@Before
	public void setUp() {
		replacer = new CenteredParagraphReplacer();
		chunks = new ArrayList<Chunk>();
	}

	@Test
	public void testReplacesCorrectly() {
		String text = "[center]Algum texto centralizado[/center] texto depois";
		Assert.assertTrue(replacer.accepts(text));
		String rest = replacer.execute(text, chunks);
		Assert.assertEquals(" texto depois", rest);
		Assert.assertEquals(1, chunks.size());
		Assert.assertEquals(CenteredParagraphChunk.class, chunks.get(0).getClass());
	}

	@Test(expected = TubainaException.class)
	public void testDoesntAcceptWithoutEndingTag() {
		String text = "[center]Algum texto centralizado";
		Assert.assertTrue(replacer.accepts(text));
		replacer.execute(text, chunks);
	}

	@Test(expected=IllegalStateException.class)
	public void testDoesntAcceptWithoutBeginTag() {
		String text = "algum texto não centralizado[/center]";
		Assert.assertFalse(replacer.accepts(text));
		replacer.execute(text, chunks);
	}
}
