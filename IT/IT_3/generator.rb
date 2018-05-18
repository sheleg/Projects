require_relative 'parse_text'

MARKOV_MODEL_ORDER = 3

def get_random_first_unit(dict)
  dict.keys.sample
end


def get_next_word(dict, unit)
  internal_hash = dict[unit]
  if internal_hash.equal?(nil)
    return
  end
  unless internal_hash.empty?
    max_v = internal_hash.values.max
    max_pair = internal_hash.select {|key, value| value.equal?(max_v)}
    max_pair.first[0]
  end
end

def get_sentence(dictionary, words_count)
  count_in_phrase = MARKOV_MODEL_ORDER
  unit = get_random_first_unit(dictionary)
  phrase = unit

  while count_in_phrase <= words_count
    phrase << get_next_word(dictionary, unit)
    unit = phrase[-MARKOV_MODEL_ORDER, MARKOV_MODEL_ORDER]
    count_in_phrase += 1
  end
  phrase
end

dictionary = get_dict(MARKOV_MODEL_ORDER)
sentence_count = 10
words_count = 10
text = []

sentence_count.times do
  text << get_sentence(dictionary, words_count)
end


# TODO: КРАСИВЫЙ ВЫВОД
(0..sentence_count).each  do |i|
  phrase = ""
  puts text[i*words_count, words_count].class
  puts text[i*words_count, words_count]
  # text[i*MARKOV_MODEL_ORDER, MARKOV_MODEL_ORDER].each do |word|
  #   phrase = phrase + " " + word.to_s
  # end
  puts phrase
end

