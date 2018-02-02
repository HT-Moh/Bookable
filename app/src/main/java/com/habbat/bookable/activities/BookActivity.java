package com.habbat.bookable.activities;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.CardView;
import android.text.Spannable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.habbat.bookable.R;
import com.habbat.bookable.helpers.ShareClass;
import com.habbat.bookable.models.ImageLinks;
import com.habbat.bookable.models.IndustryIdentifier;
import com.habbat.bookable.models.Item;
import com.habbat.bookable.models.VolumeInfo;

import org.parceler.Parcels;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import butterknife.BindView;
import butterknife.ButterKnife;
import lt.neworld.spanner.Spanner;

import static lt.neworld.spanner.Spans.bold;
import static lt.neworld.spanner.Spans.foreground;

/**
 * Created by HT-Moh on 21.12.17.
 */

public class BookActivity extends BaseActivity {

    private static final String TAG = "BookActivity" ;
    @BindView(R.id.booklinearLayout)
    LinearLayout booklinearLayout;


    @BindView(R.id.cv)
    CardView cv;
    @BindView(R.id.bookTitle)
    TextView title;
    @BindView(R.id.author)
    TextView author;
    @BindView(R.id.kind)
    TextView kind;
    @BindView(R.id.bookPhoto)
    ImageView bookPhoto;


    @BindView(R.id.publisherLabel)
    TextView publisherLabel;
    @BindView(R.id.publisherValue)
    TextView publisherValue;
    @BindView(R.id.publishedDateLabel)
    TextView publishedDateLabel;
    @BindView(R.id.publishedDateValue)
    TextView publishedDateValue;
    @BindView(R.id.description)
    TextView description;
    @BindView(R.id.industryIdentifiersLabel)
    TextView industryIdentifiersLabel;
    @BindView(R.id.readingMode)
    TextView readingMode;
    @BindView(R.id.pageCount)
    TextView pageCount;
    @BindView(R.id.printType)
    TextView printType;
    @BindView(R.id.maturityRating)
    TextView maturityRating;
    @BindView(R.id.allowAnonLogging)
    TextView allowAnonLogging;
    @BindView(R.id.panelizationSummary)
    TextView panelizationSummary;
    @BindView(R.id.imageLinks)
    RelativeLayout imageLinks;
    @BindView(R.id.language)
    TextView language;
    @BindView(R.id.previewLink)
    TextView previewLink;
    @BindView(R.id.infoLink)
    TextView infoLink;
    @BindView(R.id.canonicalVolumeLink)
    TextView canonicalVolumeLink;
    @BindView(R.id.subtitle)
    TextView subtitle;
    @BindView(R.id.averageRating)
    TextView averageRating;
    @BindView(R.id.ratingsCount)
    TextView ratingsCount;
    //@State(ItemBundler.class)

    //The book
    private Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // Icepick.restoreInstanceState(this, savedInstanceState);
        setContentView(R.layout.activity_book);
        ButterKnife.bind(this);
        //cv.setAlpha(0.3f);
        //TODO move all hardcoded text to constants class
        item = Parcels.unwrap(getIntent().getParcelableExtra("BOOK"));
        VolumeInfo volumeinfo = item.getVolumeInfo();
        title.setText(volumeinfo.getTitle());
        author.setText((volumeinfo.getAuthors()!=null && volumeinfo.getAuthors().size()>0)?volumeinfo.getAuthors().get(0):"");
        if(volumeinfo.getImageLinks()!=null && volumeinfo.getImageLinks().getSmallThumbnail()!=null){
            Glide.with(this)
                    .load(volumeinfo.getImageLinks().getSmallThumbnail())
                    .into(bookPhoto);
        }
        kind.setText(item.getKind());

        publisherValue.setText(volumeinfo.getPublisher());
        publishedDateValue.setText(volumeinfo.getPublishedDate());
        if(volumeinfo.getDescription()!=null && volumeinfo.getDescription().length()>0){
            description.setText(new Spanner("Description: "+volumeinfo.getDescription())
                    .span("Description:", foreground(Color.RED),bold()));
            description.setVisibility(View.VISIBLE);
        }
        else {
            description.setVisibility(View.INVISIBLE);
            description.setVisibility(View.GONE);
        }

        if (volumeinfo.getIndustryIdentifiers()!=null && volumeinfo.getIndustryIdentifiers().size()>0){
            String vIndInd  = "Industry Identifiers: ";
            for (IndustryIdentifier indIden: volumeinfo.getIndustryIdentifiers()){
                vIndInd+=indIden.getType() + " " +indIden.getIdentifier()+" ";
            }
            industryIdentifiersLabel.setText(new Spanner(vIndInd)
                    .span("Industry Identifiers:", foreground(Color.RED),bold()));
        }
        if(volumeinfo.readingModes!=null){
            readingMode.setText(new Spanner("Text mode:"+   (volumeinfo.readingModes.getText()?"Yes":"No")+ "\nImage mode: " + (volumeinfo.readingModes.getImage()?"Yes":"No"))
                    .span("Text mode:", foreground(Color.RED),bold())
                    .span("Image mode:", foreground(Color.RED),bold()));
        }
        pageCount.setText( new Spanner("The book contains: "+ volumeinfo.getPageCount() + " page")
                .span("The book contains:", foreground(Color.RED),bold()));

        printType.setText( new Spanner("Print Type: "+ volumeinfo.getPrintType())
                .span("Print Type:", foreground(Color.RED),bold()));

        maturityRating.setText( new Spanner("Maturity rating: "+ volumeinfo.getMaturityRating())
                .span("Maturity rating:", foreground(Color.RED),bold()));

        allowAnonLogging.setText(volumeinfo.getAllowAnonLogging()?"A non logging is allowed":"A non loggin is not allowed");
        if(volumeinfo.getPanelizationSummary()!=null){
            panelizationSummary.setText(new Spanner("Contain Epub Bubbles: " + (volumeinfo.getPanelizationSummary().containsEpubBubbles ? "Yes" : "NO")
                    +"\nContain Image Bubbles: "+(volumeinfo.getPanelizationSummary().containsImageBubbles ? "Yes" : "NO"))
                    .span("Contain Epub Bubbles:", foreground(Color.RED),bold())
                    .span("Contain Image Bubbles:", foreground(Color.RED),bold()));
        }
        if(volumeinfo.getImageLinks()!=null){
            setupImagesView(volumeinfo.getImageLinks());
        }
        language.setText(new Spanner("Book language:" + volumeinfo.getLanguage())
                .span("Total rating: ", foreground(Color.RED),bold()));
        if(volumeinfo.getSubtitle()!=null && volumeinfo.getSubtitle().length()>0){
            Spannable spannable = new Spanner("Subtitle: " + volumeinfo.getSubtitle())
                    .span("Subtitle: ", foreground(Color.RED),bold());
            subtitle.setText(spannable);
            subtitle.setVisibility(View.VISIBLE);
        }
        else {
            subtitle.setVisibility(View.INVISIBLE);
        }
        if(volumeinfo.getAverageRating()!=null && volumeinfo.getAverageRating()>0){
            averageRating.setText(new Spanner("Average rating: " + volumeinfo.getAverageRating())
                    .span("Average rating: ", foreground(Color.RED),bold()));
        }
        if(volumeinfo.getRatingsCount()!=null && volumeinfo.getRatingsCount()>0){
            averageRating.setText(new Spanner("Total rating: "+ volumeinfo.getRatingsCount())
                    .span("Total rating: ", foreground(Color.RED),bold()));
        }
    }

    public void onClick(View widget) {
        Button button = null;
        if (widget instanceof Button){
            button = (Button)widget;
        }
        Integer tag = Integer.valueOf((String)button.getTag());
        VolumeInfo volumeinfo = item.getVolumeInfo();
        switch (tag){
            case 1:
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(volumeinfo.getPreviewLink()));
                startActivity(i);
                break;
            case 2:
                Intent ii= new Intent(Intent.ACTION_VIEW);
                ii.setData(Uri.parse(volumeinfo.getInfoLink()));
                startActivity(ii);

                break;
            case 3:
                Intent iii= new Intent(Intent.ACTION_VIEW);
                iii.setData(Uri.parse(volumeinfo.getCanonicalVolumeLink()));
                startActivity(iii);
                break;
            case 4:
                String data ="Book title" + volumeinfo.getTitle() +"\n";
                data += (volumeinfo.getAuthors()!=null && volumeinfo.getAuthors().size()>0)? "Author: "+volumeinfo.getAuthors().get(0)+"\n":"";
                //data += "Preview link:\n " +
                String prvlink = null;
                try {
                    prvlink = URLEncoder.encode(volumeinfo.getPreviewLink(),java.nio.charset.StandardCharsets.UTF_8.toString());
                }
                catch(UnsupportedEncodingException e){ // Catch the encoding exception
                    e.printStackTrace();
                }
                if(prvlink!=null){
                    data +=prvlink;
                }
                ShareClass.getInstance().facebook(data,prvlink,this);
                break;
        }


    }
    private void setupImagesView(ImageLinks imageLinksValues){
        LinearLayout.LayoutParams params = new LinearLayout
                .LayoutParams(DrawerLayout.LayoutParams.MATCH_PARENT, DrawerLayout.LayoutParams.WRAP_CONTENT);
        if(imageLinksValues!=null){
           if(imageLinksValues.getThumbnail()!=null && imageLinksValues.getThumbnail().length()>0){
               ImageView imageviewThumbnail = new ImageView(this);
               imageviewThumbnail.setLayoutParams(params);
               imageLinks.addView(imageviewThumbnail);
               Glide.with(this)
                       .load(imageLinksValues.getThumbnail())
                       .into(imageviewThumbnail);
           }

            if(imageLinksValues.getSmallThumbnail()!=null && imageLinksValues.getSmallThumbnail().length()>0){
                ImageView imageviewSmallThumbnail = new ImageView(this);
                imageviewSmallThumbnail.setLayoutParams(params);
                imageLinks.addView(imageviewSmallThumbnail);
                Glide.with(this)
                        .load(imageLinksValues.getSmallThumbnail())
                        .into(imageviewSmallThumbnail);
            }

        }

    }
}
