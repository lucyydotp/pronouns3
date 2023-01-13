# Pronoun set format

<include from="snippets.topic" element-id="grammar"/>

The English language is kinda hard for computers to deal with, so to properly represent your pronouns 
we need to do it in a certain format.

Recall that there are five pronouns we need to consider:

- personal subjective
- personal objective 
- possessive
- possessive adjective
- reflexive

We also need to know whether it's singular or plural.

ProNouns stores all these pronouns separated by a slash, with the suffix `:p` if it's plural:

- `subjective/objective/possessive-adjective/possessive/reflexive` for singular sets
- `subjective/objective/possessive-adjective/possessive/reflexive:p` for plural sets

Some examples (these are all builtin, you shouldn't ever need to use these):

- `he/him/his/his/himself`
- `they/them/their/theirs/themselves:p`
