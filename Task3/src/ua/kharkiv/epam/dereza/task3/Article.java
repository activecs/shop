package ua.kharkiv.epam.dereza.task3;

public class Article {
	
	private String article;
	
	public Article() {
		super();
	}
	
	public Article(String article) {
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
		return article.length();
	}
	
	@Override
	public String toString() {
		return article;
	}
}
