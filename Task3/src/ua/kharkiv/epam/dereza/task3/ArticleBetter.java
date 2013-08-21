package ua.kharkiv.epam.dereza.task3;

public class ArticleBetter extends Article{
	
	private String article;
	
	public ArticleBetter() {
		super();
	}
	
	public ArticleBetter(String article) {
		super();
		this.article = article;
	}
	
	public String getArticle() {
		return article;
	}

	public void setArticle(String article) {
		this.article = article;
	}
	
	@Override
	public int hashCode() {
		if (article == null) return 0;
		int hash = 0;
		char value[] = article.toCharArray();
		int length = article.length() < 4 ? article.length() : 4;
		for (int i = 0; i < length; i++) {
			hash = 31 * hash + value[i];
		}
		return hash;
	}
	
	@Override
	public String toString() {		
		return article;
	}
}
