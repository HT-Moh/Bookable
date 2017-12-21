package com.habbat.bookable.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Browser;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.habbat.bookable.R;
import com.habbat.bookable.models.ImageLinks;
import com.habbat.bookable.models.IndustryIdentifier;
import com.habbat.bookable.models.Item;
import com.habbat.bookable.models.VolumeInfo;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hackolos on 21.12.17.
 */

public class BookActivity extends BaseActivity {

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
    Item item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // Icepick.restoreInstanceState(this, savedInstanceState);
        setContentView(R.layout.activity_book);
        ButterKnife.bind(this);
        //cv.setAlpha(0.3f);
        //TODO move all hardcoded text
        Item item = Parcels.unwrap(getIntent().getParcelableExtra("BOOK"));
        VolumeInfo volumeinfo = item.getVolumeInfo();
        title.setText(volumeinfo.getTitle());
        author.setText((volumeinfo!=null && volumeinfo.getAuthors()!=null && volumeinfo.getAuthors().size()>0)?volumeinfo.getAuthors().get(0):"");
        if(volumeinfo!=null && volumeinfo.getImageLinks()!=null && volumeinfo.getImageLinks().getSmallThumbnail()!=null){
            Glide.with(this)
                    .load(volumeinfo.getImageLinks().getSmallThumbnail())
                    .into(bookPhoto);
        }
        kind.setText(item.getKind());

        publisherValue.setText(volumeinfo.getPublisher());
        publishedDateValue.setText(volumeinfo.getPublishedDate());
        if(volumeinfo.getDescription()!=null && volumeinfo.getDescription().length()>0){
            description.setText(volumeinfo.getDescription());
            description.setVisibility(View.VISIBLE);
        }
        else {
            description.setVisibility(View.INVISIBLE);
        }

        if (volumeinfo.getIndustryIdentifiers()!=null && volumeinfo.getIndustryIdentifiers().size()>0){
            String vIndInd  = "Industry Identifiers:\n";
            for (IndustryIdentifier indIden: volumeinfo.getIndustryIdentifiers()){
                vIndInd+="\n"+indIden.getType() + " " +indIden.getIdentifier()+"\n";
            }
            industryIdentifiersLabel.setText(vIndInd);
        }

        if(volumeinfo.readingModes!=null){
            String readM = "";
            if(volumeinfo.readingModes.getText()){
                readM = "Text mode: Yes\n";
            }
            else {
                readM = "Text mode: No\n";
            }
            if(volumeinfo.readingModes.getImage()){
                readM += "\nImage mode: Yes";
            }
            else {
                readM += "\nImage mode: No";
            }
            readingMode.setText(readM);
        }
        pageCount.setText("The book contains: "+ volumeinfo.getPageCount() + " page" );
        printType.setText("Print Type: "+ volumeinfo.getPrintType());
        maturityRating.setText("Maturity rating: "+ volumeinfo.getMaturityRating());
        allowAnonLogging.setText(volumeinfo.getAllowAnonLogging()?"A non logging is allowed":"A non loggin is not allowed");
        String panelizationSmr = "";
        if(volumeinfo.getPanelizationSummary()!=null){
            if (volumeinfo.getPanelizationSummary().containsEpubBubbles){
                panelizationSmr += "Contain Epub Bubbles: Yes\n";
            }
            else {
                panelizationSmr += "Contain Epub Bubbles: No\n";
            }
            if (volumeinfo.getPanelizationSummary().containsImageBubbles){
                panelizationSmr += "\nContain Image Bubbles: Yes";
            }
            else {
                panelizationSmr += "\nContain Image Bubbles: No";
            }

        }

        panelizationSummary.setText(panelizationSmr);
        if(volumeinfo.getImageLinks()!=null){
            setupImagesView(volumeinfo.getImageLinks());
//            imageLinks.setText("Small thumbnail -> "+volumeinfo.getImageLinks().getSmallThumbnail()
//            + "\nthumbnail -> " + volumeinfo.getImageLinks().getThumbnail()
//            );
        }
        language.setText("Book language:" + volumeinfo.getLanguage());
        previewLink.setText("Preview link:\n " + volumeinfo.getPreviewLink());

        infoLink.setText("Info link:\n " + volumeinfo.getInfoLink());
        canonicalVolumeLink.setText("Canonical volume link:\n" + volumeinfo.getCanonicalVolumeLink());
        if(volumeinfo.getSubtitle()!=null){
            subtitle.setText("Subtitle: " + volumeinfo.getSubtitle());
            subtitle.setVisibility(View.VISIBLE);
        }
        else {
            subtitle.setVisibility(View.INVISIBLE);
        }
        if(volumeinfo!=null && volumeinfo.getAverageRating() instanceof Double && volumeinfo.getAverageRating()>0){
            averageRating.setText("Average rating: " + volumeinfo.getAverageRating());
        }
        if(volumeinfo!=null &&  volumeinfo.getRatingsCount() instanceof Integer && volumeinfo.getRatingsCount()>0){
            ratingsCount.setText("Total rating: "+ volumeinfo.getRatingsCount());
        }
    }

    public void onClick(View widget) {
        if (widget instanceof TextView){
            TextView tmp = (TextView) widget;
            Uri uri = Uri.parse(tmp.getText().toString());
            Context context = widget.getContext();
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.putExtra(Browser.EXTRA_APPLICATION_ID, context.getPackageName());
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
