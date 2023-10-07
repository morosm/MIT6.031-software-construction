/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Extract consists of methods that extract information from a list of tweets.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Extract {

    /**
     * Get the time period spanned by tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return a minimum-length time interval that contains the timestamp of
     *         every tweet in the list.
     */
    public static Timespan getTimespan(List<Tweet> tweets) {
    	var stream = tweets.stream()
    			.map(t -> t.getTimestamp())
    			.sorted()
    			.toList();
    	var result = new Timespan(stream.get(0), stream.get(stream.size()-1));
    	return result;
    }

    /**
     * Get usernames mentioned in a list of tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return the set of usernames who are mentioned in the text of the tweets.
     *         A username-mention is "@" followed by a Twitter username (as
     *         defined by Tweet.getAuthor()'s spec).
     *         The username-mention cannot be immediately preceded or followed by any
     *         character valid in a Twitter username.
     *         For this reason, an email address like bitdiddle@mit.edu does NOT 
     *         contain a mention of the username mit.
     *         Twitter usernames are case-insensitive, and the returned set may
     *         include a username at most once.
     */
    public static Set<String> getMentionedUsers(List<Tweet> tweets) {
        var tmp = tweets.stream()
        		.map(t -> getSingleTweetMentionedUsers(t))
        		.flatMap(p -> p.stream())
        		.toList();
        var result = new HashSet<String>(tmp);
        
        return result;
    }
    
    private static Set<String> getSingleTweetMentionedUsers(Tweet tweet){
    	var text = tweet.getText().toLowerCase();
        Pattern pattern = Pattern.compile("@([A-Za-z0-9_]+)");
        var mentions = new HashSet<String>();
        
        var matcher = pattern.matcher(text);
        
        // 查找匹配的Twitter用户名并添加到集合中
        while (matcher.find()) {
            String mention = matcher.group(1).toLowerCase(); // 转换为小写，Twitter用户名不区分大小写
            mentions.add(mention);
        }
        
        return mentions;
    	
    }
}
