package com.netcomps.oauth_example;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter implements Filterable {
	Context context;
	static ArrayList<MailContacts> mList;
	ArrayList<MailContacts> secondList;
	public static ArrayList<MailContacts> list = new ArrayList<MailContacts>();
	public static ArrayList<MailContacts> rList = new ArrayList<MailContacts>();

	private Filter arrangeFilter;

	public MyAdapter(Context applicationContext, ArrayList<MailContacts> mList) {
		// TODO Auto-generated constructor stub
		context = applicationContext;
		this.mList = mList;
		secondList = mList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public MailContacts getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewHolder holder;
		LayoutInflater mInflater;
		if (convertView == null) {
			mInflater = LayoutInflater.from(context);
			convertView = mInflater.inflate(R.layout.inflate, null);
			holder = new ViewHolder();
			holder.eMail = (TextView) convertView.findViewById(R.id.gmail);
			holder.contactName = (TextView) convertView
					.findViewById(R.id.contactname);
			holder.mCheckBox = (CheckBox) convertView
					.findViewById(R.id.rawCheckBox);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.eMail.setText(mList.get(position).getContactEmail());
		holder.contactName.setText(mList.get(position).getContactName());
		// holder.mCheckBox.setChecked(mList.isSelected());
		holder.mCheckBox
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (holder.mCheckBox.isChecked()) {

							if (!selectedEmails.contains(getItem(position))) {
								selectedEmails.add(getItem(position)
										.getContactEmail().trim());
							}
						} else {

							selectedEmails.remove(getItem(position)
									.getContactEmail().trim());
						}

					}
				});/* () { */

		/*
		 * @Override public void onClick(View v) { if
		 * (holder.mCheckBox.isChecked()) { MailContacts mailContacts = new
		 * MailContacts(); mailContacts.setContactEmail(mList.get(position)
		 * .getContactEmail()); list.add(mailContacts);
		 * 
		 * 
		 * } else if (!(holder.mCheckBox.isChecked())) {
		 * list.remove(mailContacts);
		 * System.out.println("I am in else if part"); MailContacts mailContacts
		 * = new MailContacts(); //
		 * mailContacts.setContactEmail(mList.get(position) //
		 * .getContactEmail());
		 * mailContacts.setContactEmail(mList.get(position).getContactEmail());
		 * System
		 * .out.println("the value"+mList.get(position).getContactEmail());
		 * //list.remove(mailContacts); }
		 * 
		 * else { try { System.out.println("iam in else loop"); MailContacts
		 * mailContacts = new MailContacts(); mailContacts = list.get(position);
		 * list.remove(mailContacts); } catch(IndexOutOfBoundsException ioe) {
		 * System.out.println("Error"+ioe); } }
		 * 
		 * else { System.out.println("I am in removal part"); MailContacts
		 * mailContacts = new MailContacts();
		 * mailContacts.removeContactEmail(mList.get(position)
		 * .getContactEmail()); list.remove(mailContacts); }
		 * 
		 * } });
		 */
		/*
		 * 
		 */

		return convertView;

	}

	protected static ArrayList<String> selectedEmails = new ArrayList<String>();

	public void resetData() {
		mList = secondList;
	}

	class ViewHolder {

		TextView eMail;
		TextView contactName;
		CheckBox mCheckBox;
	}

	@Override
	public Filter getFilter() {
		if (arrangeFilter == null)
			arrangeFilter = new ArrangeFilter();

		return arrangeFilter;
	}

	private class ArrangeFilter extends Filter {

		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			FilterResults results = new FilterResults();
			// We implement here the filter logic
			if (constraint == null || constraint.length() == 0) {
				// No filter implemented we return all the list
				results.values = secondList;
				results.count = secondList.size();
			} else {
				// We perform filtering operation
				List<MailContacts> nArrangedList = new ArrayList<MailContacts>();

				for (MailContacts p : mList) {
					if (p.getContactEmail().toUpperCase()
							.startsWith(constraint.toString().toUpperCase()))
						nArrangedList.add(p);
				}

				results.values = nArrangedList;
				results.count = nArrangedList.size();

			}
			return results;
		}

		@Override
		protected void publishResults(CharSequence constraint,
				FilterResults results) {

			// Now we have to inform the adapter about the new list filtered
			if (results.count == 0)
				notifyDataSetInvalidated();
			else {
				mList = (ArrayList<MailContacts>) results.values;
				notifyDataSetChanged();
			}

		}

	}

}
