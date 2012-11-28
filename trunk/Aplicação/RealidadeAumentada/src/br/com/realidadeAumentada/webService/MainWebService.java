package br.com.realidadeAumentada.webService;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import br.com.realidadeAumentada.R;

public class MainWebService extends Activity {
	ImageView img;
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        	        
	        setContentView(R.layout.main);
	        //---download an image---
//	        Bitmap bitmap =
//	        	DownloadImage(
//	            "http://www.muitosgatos.com/images/imagens-gatos.jpg");
//	        img = (ImageView) findViewById(R.id.img);
//	        img.setImageBitmap(bitmap);
	        
//	        //---download an RSS feed---
//	        String str = DownloadText(
//	        "http://www.appleinsider.com/appleinsider.rss");
//	        Toast.makeText(getBaseContext(), str,
//	                       Toast.LENGTH_SHORT).show();
//	        //---access a Web service using GET---
//	        WordDefinition("Apple");
	    }
	

//	@SuppressWarnings("unused")
//	private InputStream OpenHttpConnection(String urlString) throws IOException
//	{
//		return null;
//		
//	}
//	
//	  private Bitmap DownloadImage(String URL)
//	    {
//	        Bitmap bitmap = null;
//	        InputStream in = null;
//	        try {
//	            in = OpenHttpConnection(URL);
//	            bitmap = BitmapFactory.decodeStream(in);
//	            in.close();
//	        } catch (IOException e1) {
//	            Toast.makeText(this, e1.getLocalizedMessage(), 
//	                Toast.LENGTH_LONG).show();
//	            e1.printStackTrace();
//	        }
//	        return bitmap;
//	    }

//	 private void WordDefinition(String word) {
//			InputStream in = null;
//			
//			try {
//			in = OpenHttpConnection(
//			"http://services.aonaware.com/DictService/DictService.asmx/Define word=" + word);
//			Document doc = null;
//			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//			DocumentBuilder db;
//			try {
//			db = dbf.newDocumentBuilder();
//	                doc = db.parse(in);
//	            } catch (ParserConfigurationException e) {
//	                // TODO Auto-generated catch block
//	                e.printStackTrace();
//	            } catch (Exception e) {
//	                // TODO Auto-generated catch block
//	                e.printStackTrace();
//	            }
//	            doc.getDocumentElement().normalize();
//	            //---retrieve all the <Definition> nodes---
//	            NodeList itemNodes = doc.getElementsByTagName("Definition");
//	            String strDefinition = "";
//	            for (int i = 0; i < itemNodes.getLength(); i++) {
//		            Node itemNode = itemNodes.item(i);
//	                if (itemNode.getNodeType() == Node.ELEMENT_NODE)
//	                {
//	                    //---convert the Node into an Element---
//	                    Element definitionElement = (Element) itemNode;
//	                    //---get all the <WordDefinition> elements under
//	                    // the <Definition> element---
//	                    NodeList wordDefinitionElements = 
//	                    	(NodeList) definitionElement.getChild("WordDefinition");
//	                    strDefinition = "";
//	                    for (int j = 0; j < wordDefinitionElements.getLength(); j++) {
//	                        //---convert a <WordDefinition> Node into an Element---
//	                        Element wordDefinitionElement =
//	                            (Element) wordDefinitionElements.item(j);
//	                        //---get all the child nodes under the
//	                        // <WordDefinition> element---
//	                        NodeList textNodes =
//	                            ((Node) wordDefinitionElement).getChildNodes();
//	                        strDefinition +=
//	                            ((Node) textNodes.item(0)).getNodeValue() + ". ";
//	                    }
//	                    
//	                    //---display the title---
//	                    Toast.makeText(getBaseContext(),strDefinition,
//	                        Toast.LENGTH_SHORT).show();
//	                }
//            }
//        } catch (IOException e1) { 
//        	Toast.makeText(this, e1.getLocalizedMessage(),
//            Toast.LENGTH_LONG).show();
//        	e1.printStackTrace();
//        }
//	 }
}	 