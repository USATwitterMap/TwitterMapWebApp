package view;

public class TwitterSearchView 
{
	private SingleWordView singleWordView[];
	private MultiWordView multiWordView;
	private LineGraphView lineGraphView;

	public SingleWordView[] getSingleWordView() {
		return singleWordView;
	}

	public void setSingleWordView(SingleWordView[] singleWordView) {
		this.singleWordView = singleWordView;
	}

	public MultiWordView getMultiWordView() {
		return multiWordView;
	}

	public void setMultiWordView(MultiWordView multiWordView) {
		this.multiWordView = multiWordView;
	}

	public LineGraphView getLineGraphView() {
		return lineGraphView;
	}

	public void setLineGraphView(LineGraphView lineGraphView) {
		this.lineGraphView = lineGraphView;
	}
}
