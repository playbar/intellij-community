'{0.attr[item]:{foo[item]:5} {bar.attr:{baz}}}'.format(42, foo=func(), bar=MyClass(1, 2), baz=unused)