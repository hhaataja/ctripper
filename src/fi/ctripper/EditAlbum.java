package fi.ctripper;

import java.util.LinkedList;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageView;

public class EditAlbum extends FragmentActivity {
	
	public final static int ADD_PHOTO_RESULT = 1001;
	private static final String TAG = "EditAlbum";

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_album);

		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_album, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:{
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		case R.id.addSection:{
			this.mSectionsPagerAdapter.addSection();
		}

		case R.id.removeSection:{
//			this.mSectionsPagerAdapter.removeSection(position)
		}
			
		}
		return super.onOptionsItemSelected(item);
	}
	
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent returnedIntent) {
//		super.onActivityResult(requestCode, resultCode, returnedIntent);
//		
//		switch(requestCode){
//			case ADD_PHOTO_RESULT:{
//				if(resultCode == RESULT_OK){
//			        Uri selectedImage = returnedIntent.getData();
//			        PhotoSectionFragment fragment = (PhotoSectionFragment) mSectionsPagerAdapter.getItem(1);
//			        Log.d(TAG, selectedImage.toString());
//			        fragment.setImage(selectedImage);
//				}
//			}
//		}
//		
//	}
	
//	public void showBrowsePhotoDialog(View view){
//		Intent pickPhoto = new Intent(Intent.ACTION_PICK,
//		           android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//		startActivityForResult(pickPhoto , 1); //one can be replced with any action code
//	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {
		
		LinkedList<Fragment> fragmentsList = new LinkedList<Fragment>();

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
			this.addSection();
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			Fragment fragment = fragmentsList.get(position);			
			Bundle args = new Bundle();
			args.putInt(PhotoSectionFragment.ARG_SECTION_NUMBER, position + 1);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			return fragmentsList.size();
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return "Page-"+position;
		}
		
		public void removeSection(int position){
			this.fragmentsList.remove(position);
		}
		
		public void addSection(){
			this.fragmentsList.add(new PhotoSectionFragment());
		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class PhotoSectionFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";
		public static final String ARG_PHOTO_URI = "photo_uri";
		private Uri imageUri;
		private View view;
		private OnItemSelectedListener listener;

		public PhotoSectionFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			// Prepare root view
			View rootView = inflater.inflate(
					R.layout.fragment_album_photo, container, false);
			this.view = rootView;
			
			// Prepare Image view if image uri is set
			Uri uri = (Uri) this.getArguments().get(ARG_PHOTO_URI);			
			if(uri != null){
				setImageUri(uri);
			}
			
			if(this.getImageUri() != null){
				ImageView imageView = (ImageView) rootView.findViewById(R.id.photoImageView);
				imageView.setImageURI(uri);
			}
			
		    ImageView imageView = (ImageView) rootView.findViewById(R.id.photoImageView);
		    imageView.setOnClickListener(new View.OnClickListener() {
		      @Override
		      public void onClick(View v) {
		        browseForPhoto();
		      }
		    });
			
			return rootView;
		}
		
		@Override
		public void onResume() {
			Log.d(TAG, "PhotoFragment resumes");
			updateImageToView();
			super.onResume();
		}		
		
		private void updateImageToView(){
			if(this.imageUri != null){
				ImageView imageView = (ImageView) this.view.findViewById(R.id.photoImageView);
				imageView.setImageURI(this.imageUri);
				imageView.refreshDrawableState();
			}
		}

		public Uri getImageUri() {
			return imageUri;
		}

		public void setImageUri(Uri imageUri) {
			this.imageUri = imageUri;
		}
		
		public void browseForPhoto(){
			Intent pickPhoto = new Intent(Intent.ACTION_PICK,
			           android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			startActivityForResult(pickPhoto , ADD_PHOTO_RESULT);
		}
		
		public void onActivityResult(int requestCode, int resultCode, Intent returnedIntent) {
			super.onActivityResult(requestCode, resultCode, returnedIntent);
			Log.d(TAG, "OnActivityResult with requestCode:"+requestCode);
			switch(requestCode){
				case ADD_PHOTO_RESULT:{
					if(resultCode == RESULT_OK){
				        Uri selectedImage = returnedIntent.getData();
				        Log.d(TAG, selectedImage.toString());
				        this.setImageUri(selectedImage);
				        this.updateImageToView();
					}
				}
			}
			
		}
		
	}

}
